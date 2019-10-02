import { Component, OnInit } from "@angular/core";
import { BoardService } from "../../board.service";
import { Field } from "../../models/field.model";
import { Attack } from "../../models/attack.model";

@Component({
  selector: "app-robot",
  templateUrl: "./robot.component.html",
  styleUrls: ["./robot.component.css"]
})
export class RobotComponent implements OnInit {
  constructor(private boardService: BoardService) {}

  board: Field[][];
  size: number[] = [];
  war: Field[] = [];
  invader: Field;
  invaded: Field;
  whosTurn: boolean = true;
  invaderPoints: number;
  enemyPoints: number;
  count: number = 0;

  ngOnInit() {
    this.boardService.initBoard().subscribe((data: []) => {
      this.convertBoard(data);
      for (let i = 0; i < Math.sqrt(data.length); i++) {
        this.size[i] = i;
      }
    });
  }

  attack(f: Field) {
    if (this.invader == undefined) {
      this.invader = f;
      this.war.push(f);
    } else {
      if (this.invader != undefined && this.war[0] == f) {
        this.war = [];
        this.invader = undefined;
      } else {
        this.war.push(f);
        this.boardService.attack(this.war).subscribe((data: number[]) => {
          this.invaderPoints = data[0];
          this.enemyPoints = data[1];
          this.getBoard();
          this.war = [];
          this.invader = undefined;
        });
      }
    }
  }

  attackable(f: Field) {
    if (this.whosTurn == false) {
      return true;
    } else {
      if (this.whosTurn == f.owner && this.invader == undefined) {
        if (f.diceNumber == 1) {
          return true;
        } else {
          return false;
        }
      } else {
        if (this.invader != undefined && f.owner == !this.invader.owner) {
          if (
            (Math.abs(f.row - this.invader.row) == 1 &&
              Math.abs(f.col - this.invader.col) == 0) ||
            (Math.abs(f.row - this.invader.row) == 0 &&
              Math.abs(f.col - this.invader.col) == 1)
          ) {
            return false;
          } else {
            return true;
          }
        } else if (this.invader != undefined && this.invader == f) {
          return false;
        } else {
          return true;
        }
      }
    }
  }

  endTurn() {
    this.boardService.endTurn(this.whosTurn).subscribe(() => {
      this.whosTurn = !this.whosTurn;
      this.boardService.getBoard().subscribe((data: []) => {
        this.convertBoard(data);
        this.boardService.robotAttack().subscribe((data2: Attack[]) => {
          this.myLoop(data2, 0);
        });
      });
    });
  }

  myLoop(data2: Attack[], count: number) {
    setTimeout(() => {
      let thisCount = count;
      if (data2.length != 0) {
        this.invaderPoints = data2[thisCount].invaderPoints;
        this.enemyPoints = data2[thisCount].invadedPoints;
        this.convertBoard(data2[thisCount].board);
        this.invader = data2[thisCount].invader;
        this.invaded = data2[thisCount].invaded;
        thisCount++;
      }
      if (thisCount < data2.length) {
        this.myLoop(data2, thisCount);
      }
      if (thisCount == data2.length) {
        this.boardService.endTurn(this.whosTurn).subscribe(() => {
          this.whosTurn = !this.whosTurn;
          this.getBoard();
          this.war = [];
          this.invader = undefined;
          this.invaded = undefined;
        });
      }
    }, 1000);
  }

  getBoard() {
    this.boardService.getBoard().subscribe((data: []) => {
      this.convertBoard(data);
    });
  }

  convertBoard(data: Field[]) {
    this.board = [];
    let k = 0;
    for (let i: number = 0; i < Math.sqrt(data.length); i++) {
      this.board[i] = [];
      for (let j: number = 0; j < Math.sqrt(data.length); j++) {
        this.board[i][j] = data[k];
        k++;
      }
    }
  }

  resume() {
    this.boardService.resume().subscribe((data: Field[]) => {
      this.convertBoard(data);
    });
  }
}
