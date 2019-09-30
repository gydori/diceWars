import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { StartPageComponent } from "./components/start-page/start-page.component";
import { BoardComponent } from "./components/board/board.component";
import { RouterModule, Routes } from "@angular/router";
import { FieldComponent } from "./components/field/field.component";
import { BoardService } from "./board.service";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { RobotComponent } from "./components/robot/robot.component";
import { HttpConfigInterceptor } from "./httpconfig.interceptor";
import { TitleComponent } from "./components/title/title.component";
import { WinComponent } from "./components/win/win.component";
import { LostComponent } from "./components/lost/lost.component";

const myRoutes: Routes = [
  { path: "", component: StartPageComponent },
  { path: "game", component: BoardComponent },
  { path: "gameRobot", component: RobotComponent },
  { path: "win", component: WinComponent },
  { path: "lost", component: LostComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent,
    BoardComponent,
    FieldComponent,
    RobotComponent,
    TitleComponent,
    WinComponent,
    LostComponent
  ],
  imports: [BrowserModule, RouterModule.forRoot(myRoutes), HttpClientModule],
  providers: [
    BoardService,
    { provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
