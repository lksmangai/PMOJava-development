export interface IBacklogPractice {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
}

export class BacklogPractice implements IBacklogPractice {
  constructor(public id?: number, public code?: string, public name?: string, public description?: string) {}
}
