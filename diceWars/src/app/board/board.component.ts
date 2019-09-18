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

  ngOnInit() {
    this.boardService.initBoard().subscribe((data: []) => {
      this.board = [];
      let k = 0;
      for (let i: number = 0; i < Math.sqrt(data.length); i++) {
        this.board[i] = [];
        for (let j: number = 0; j < Math.sqrt(data.length); j++) {
          this.board[i][j] = data[k];
          k++;
        }
      }

      for (let i = 0; i < Math.sqrt(data.length); i++) {
        this.size[i] = i;
      }

      console.log(this.board);
    });
  }

  attack(f: Field) {
    if (this.invader == undefined) {
      this.invader = f;
      this.war.push(f);
    } else {
      this.war.push(f);
      console.log(this.war);
      this.boardService.attack(this.war).subscribe(() => {
        this.boardService.getBoard().subscribe((data: []) => {
          this.board = [];
          let k = 0;
          for (let i: number = 0; i < Math.sqrt(data.length); i++) {
            this.board[i] = [];
            for (let j: number = 0; j < Math.sqrt(data.length); j++) {
              this.board[i][j] = data[k];
              k++;
            }
          }
          this.war = [];
          this.invader = undefined;
          console.log(this.war);
        });
      });
    }
  }

  attackable(f: Field) {
    if (this.invader == undefined) {
      return false;
    } else {
      if (f.owner == !this.invader.owner) {
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
      } else {
        return true;
      }
    }
  }
}
