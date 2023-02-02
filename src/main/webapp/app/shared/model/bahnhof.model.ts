export interface IBahnhof {
  id?: number;
  bahnhofsNr?: number | null;
  bahnhofsName?: string | null;
}

export class Bahnhof implements IBahnhof {
  constructor(public id?: number, public bahnhofsNr?: number | null, public bahnhofsName?: string | null) {}
}
