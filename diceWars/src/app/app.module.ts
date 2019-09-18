import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { StartPageComponent } from "./start-page/start-page.component";
import { BoardComponent } from "./board/board.component";
import { RouterModule, Routes } from "@angular/router";
import { FieldComponent } from "./field/field.component";
import { BoardService } from "./board.service";
import { HttpClientModule } from "@angular/common/http";

const myRoutes: Routes = [
  { path: "", component: StartPageComponent },
  { path: "game", component: BoardComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent,
    BoardComponent,
    FieldComponent
  ],
  imports: [BrowserModule, RouterModule.forRoot(myRoutes), HttpClientModule],
  providers: [BoardService],
  bootstrap: [AppComponent]
})
export class AppModule {}
