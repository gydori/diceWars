import { Component, OnInit, Input } from "@angular/core";
import { Field } from "../field.model";

@Component({
  selector: "app-field",
  templateUrl: "./field.component.html",
  styleUrls: ["./field.component.css"]
})
export class FieldComponent implements OnInit {
  @Input() field: Field;

  constructor() {}

  ngOnInit() {}
}
