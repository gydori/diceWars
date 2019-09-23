import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { StartPageComponent } from "./start-page/start-page.component";
import { BoardComponent } from "./board/board.component";
import { RouterModule, Routes } from "@angular/router";
import { FieldComponent } from "./field/field.component";
import { BoardService } from "./board.service";
import { HttpClientModule } from "@angular/common/http";
import { RobotComponent } from "./robot/robot.component";

const myRoutes: Routes = [
  { path: "", component: StartPageComponent },
  { path: "game", component: BoardComponent },
  { path: "gameRobot", component: RobotComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent,
    BoardComponent,
    FieldComponent,
    RobotComponent
  ],
  imports: [BrowserModule, RouterModule.forRoot(myRoutes), HttpClientModule],
  providers: [BoardService],
  bootstrap: [AppComponent]
})
export class AppModule {}
