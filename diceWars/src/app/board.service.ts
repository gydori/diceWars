import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Field } from "./models/field.model";
import { API_BASE_URL } from "../constants";

@Injectable({
  providedIn: "root"
})
export class BoardService {
  constructor(private httpClient: HttpClient) {}

  initBoard() {
    return this.httpClient.get(`${API_BASE_URL}/board`);
  }

  attack(war: Field[]) {
    return this.httpClient.post(`${API_BASE_URL}/attack`, war);
  }

  getBoard() {
    return this.httpClient.get(`${API_BASE_URL}/getboard`);
  }

  endTurn(whosTurn: boolean) {
    return this.httpClient.post(`${API_BASE_URL}/endturn`, String(whosTurn));
  }

  robotAttack() {
    return this.httpClient.get(`${API_BASE_URL}/robot`);
  }
}
