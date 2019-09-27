import { Component, OnInit } from "@angular/core";
import { BoardService } from "../board.service";
import { Field } from "src/app/field.model";

@Component({
  selector: "app-board",
  templateUrl: "./board.component.html",
  styleUrls: ["./board.component.css"]
})
export class BoardComponent implements OnInit {
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
  }

  attackable(f: Field) {
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

  endTurn() {
    console.log(this.whosTurn);
    this.boardService.endTurn(this.whosTurn).subscribe(() => {
      this.whosTurn = !this.whosTurn;
      this.getBoard();
    });
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
