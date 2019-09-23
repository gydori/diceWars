import { Component, OnInit } from "@angular/core";
import { BoardService } from "../board.service";
import { Field } from "../field.model";
import { Attack } from "../attack.model";
import { SourceListMap } from "source-list-map";

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
  whosTurn: boolean = true;
  invaderPoints: number;
  enemyPoints: number;

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
    console.log(this.war);
  }

  attackable(f: Field) {
    if (this.whosTurn == false) {
      return true;
    } else {
      if (this.whosTurn == f.owner && this.invader == undefined) {
        return false;
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
          this.robotAttack(data2);
          this.boardService.endTurn(this.whosTurn).subscribe(() => {
            this.whosTurn = !this.whosTurn;
            this.getBoard();
          });
        });
      });
    });
  }

  /*  myLoop(data2: Attack[]) {
    setTimeout(function() {
      this.robotAttack(data2[this.i]);
      this.i++;
      if (this.i < data2.length) {
        this.myLoop();
      }
    }, 3000);
  }*/

  robotAttack(data: Attack[]) {
    for (let i = 0; i < data.length; i++) {
      this.invader = data[i].invader;
      this.war = [];
      this.war.push(this.invader);
      this.war.push(data[i].invaded);
      this.boardService.attack(this.war).subscribe((data3: number[]) => {
        this.invaderPoints = data3[0];
        this.enemyPoints = data3[1];
        this.getBoard();
        this.war = [];
        this.invader = undefined;
      });
    }
  }

  getBoard() {
    this.boardService.getBoard().subscribe((data: []) => {
      this.convertBoard(data);
    });
  }

  convertBoard(data: []) {
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
}
