export class Error {
  constructor(
    public status: string,
    public reason: string,
    public stackTrace = [],
    public suppressedExceptions = []
  ) {}
}
