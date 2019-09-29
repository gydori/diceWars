export class Field {
  constructor(
    public id: number,
    public row: number,
    public col: number,
    public owner: boolean,
    public diceNumber: number
  ) {}
}
