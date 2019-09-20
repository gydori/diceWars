import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Field } from "./field.model";

@Injectable({
  providedIn: "root"
})
export class BoardService {
  constructor(private httpClient: HttpClient) {}

  initBoard() {
    return this.httpClient.get("http://localhost:8081/board");
  }

  attack(war: Field[]) {
    return this.httpClient.post("http://localhost:8081/attack", war);
  }

  getBoard() {
    return this.httpClient.get("http://localhost:8081/getboard");
  }

  endTurn(whosTurn: boolean) {
    return this.httpClient.post(
      "http://localhost:8081/endgame",
      String(whosTurn)
    );
  }
}
