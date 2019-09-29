import { Field } from "./field.model";

export class Attack {
  constructor(
    public invader: Field,
    public invaded: Field,
    public invaderPoints: number,
    public invadedPoints: number,
    public board: Field[]
  ) {}
}
