export class User {
  firstName: string;
  lastName: string;
  title: string;
  username: string;
  email: string;
  password: string;
  state: string;
  city: string;
  reviewer: boolean;
  private _fields: Field[];

  get fields(): Field[] {
    return this._fields;
  }

  set fields(value: Field[]) {
    this._fields = value;
  }

  constructor(firstName: string, lastName: string, title: string, username: string, email: string, password: string, state: string, city: string,
              reviwer: boolean) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.title = title;
    this.username = username;
    this.email = email;
    this.password = password;
    this.state = state;
    this.city = city;
    this.reviewer = reviwer;
  }

}

export class Magazine {
  id: number;
  name: string;
  issn: string;
  paymentTypes: PaymentType[];
  fields: Field[];

  constructor(id: number, name: string, issn: string, paymentTypes: PaymentType[], fields: Field[]) {
    this.id = id;
    this.name = name;
    this.issn = issn;
    this.paymentTypes = paymentTypes;
    this.fields = fields;
  }
}

export class PaymentType {
  id: number;
  name: string;


  constructor(id: number, name: string) {
    this.id = id;
    this.name = name;
  }
}

export class Field {
  name: string;

  constructor(name: string) {
    this.name = name;
  }
}
