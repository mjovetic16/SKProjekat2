import {Reservation} from "./reservation.model";


export class User {
    constructor( public username: string,
                 public password: string,
                 public type: string,
                 public bookings: Reservation[]) {}
 
}