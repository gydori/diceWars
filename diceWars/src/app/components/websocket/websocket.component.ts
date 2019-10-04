import { Component } from "@angular/core";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import { Field } from "src/app/models/field.model";
import { ActivatedRoute, Router } from "@angular/router";
import { Error } from "../../models/error.model";
import { socketMessage } from "src/app/models/socketMessage.model";
import { Attack } from "src/app/models/attack.model";
import { API_BASE_URL } from "../../../constants";

@Component({
  selector: "app-root",
  templateUrl: "./websocket.component.html",
  styleUrls: ["./websocket.component.css"]
})
export class WebsocketComponent {
  private serverUrl = `${API_BASE_URL}/socket`;
  private stompClient;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
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
        let messageP: socketMessage = JSON.parse(message.body);
        if (messageP.title == "attack") {
          let attack: Attack = messageP.body;
          that.invaderPoints = attack.invaderPoints;
          that.enemyPoints = attack.invadedPoints;
          that.invader = attack.invader;
          that.invaded = attack.invaded;
        }
        if (messageP.title == "initBoard" || messageP.title == "getBoard") {
          that.board = [];
          let k = 0;
          for (let i: number = 0; i < Math.sqrt(messageP.body.length); i++) {
            that.board[i] = [];
            for (let j: number = 0; j < Math.sqrt(messageP.body.length); j++) {
              that.board[i][j] = messageP.body[k];
              k++;
            }
          }
        }
        if (messageP.title == "whosTurn") {
          that.whosTurn = !JSON.parse(messageP.body);
        }
        if (messageP.title == "endGame") {
          let err: Error = messageP.body;
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
        if (messageP.title == "emptyWar") {
          that.war = [];
          that.invader = undefined;
          that.invaded = undefined;
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
          setTimeout(() => {
            this.sendMessage("emptyWar", "emptyWar");
          }, 500);
        }, 1000);
      }
    }
  }

  attackable(f: Field) {
    if (String(this.whosTurn) != this.url) {
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
    this.sendMessage("whosTurn", String(this.whosTurn));
    setTimeout(() => {
      this.sendMessage("getBoard", "getBoard");
    }, 500);
  }
}
