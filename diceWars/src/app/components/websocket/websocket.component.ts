import { Component } from "@angular/core";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import { BoardService } from "../../board.service";
import { Field } from "src/app/models/field.model";
import { ActivatedRoute, Router } from "@angular/router";
import { Error } from "../../models/error.model";

@Component({
  selector: "app-root",
  templateUrl: "./websocket.component.html",
  styleUrls: ["./websocket.component.css"]
})
export class WebsocketComponent {
  private serverUrl = "http://localhost:8081/socket";
  private stompClient;

  constructor(
    private boardService: BoardService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.initializeWebSocketConnection();
  }

  board: Field[][];
  war: Field[] = [];
  invader: Field;
  invaded: Field;
  whosTurn: boolean = true;
  invaderPoints: number;
  enemyPoints: number;
  url = this.activatedRoute.snapshot.url[1].path;

  ngOnInit() {
    /*this.boardService.initBoard().subscribe((data: []) => {
      this.convertBoard(data);
      for (let i = 0; i < Math.sqrt(data.length); i++) {
        this.size[i] = i;
      }
    });*/
    setTimeout(() => {
      this.sendMessage("initBoard", "initBoard");
    }, 500);
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/chat", message => {
        var messageP = JSON.parse(message.body);
        console.log(messageP);
        if (messageP.length == 2) {
          that.invaderPoints = messageP[0];
          that.enemyPoints = messageP[1];
        } else {
          if (messageP.length > 2) {
            that.board = [];
            let k = 0;
            for (let i: number = 0; i < Math.sqrt(messageP.length); i++) {
              that.board[i] = [];
              for (let j: number = 0; j < Math.sqrt(messageP.length); j++) {
                that.board[i][j] = messageP[k];
                k++;
              }
            }
          } else {
            if (messageP === true || messageP === false) {
              that.whosTurn = !messageP;
            } else {
              let err: Error = messageP;
              if (err.reason === "You win") {
                if (that.url === "true") {
                  that.router.navigate(["win"]);
                } else {
                  that.router.navigate(["lost"]);
                }
              } else {
                if (that.url === "true") {
                  that.router.navigate(["lost"]);
                } else {
                  that.router.navigate(["win"]);
                }
              }
            }
          }
        }
      });
    });
  }

  sendMessage(header, message) {
    this.stompClient.send("/app/send/message", { header }, message);
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
        this.invaded = f;
        this.war.push(f);
        this.sendMessage("war", JSON.stringify(this.war));
        setTimeout(() => {
          this.sendMessage("getBoard", "getBoard");
        }, 500);

        /*this.boardService.attack(this.war).subscribe((data: number[]) => {
          this.invaderPoints = data[0];
          this.enemyPoints = data[1];
          this.getBoard();
          this.war = [];
          this.invader = undefined;
        });*/
        setTimeout(() => {
          this.war = [];
          this.invader = undefined;
          this.invaded = undefined;
        }, 500);
      }
    }
  }

  attackable(f: Field) {
    if (String(this.whosTurn) != this.activatedRoute.snapshot.url[1].path) {
      return true;
    } else {
      if (this.invader == undefined && this.whosTurn == f.owner) {
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

  isYourTurn() {
    if (String(this.whosTurn) === this.url) {
      return false;
    } else {
      return true;
    }
  }

  endTurn() {
    console.log(this.whosTurn);
    this.sendMessage("whosTurn", String(this.whosTurn));
    //this.whosTurn = !this.whosTurn;
    setTimeout(() => {
      this.sendMessage("getBoard", "getBoard");
    }, 500);

    /*this.boardService.endTurn(this.whosTurn).subscribe(() => {
      this.whosTurn = !this.whosTurn;
      this.getBoard();
    });*/
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
}
