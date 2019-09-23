import { Field } from "./field.model";

export class Attack {
  constructor(
    public id: number,
    public invader: Field,
    public invaded: Field
  ) {}
}
