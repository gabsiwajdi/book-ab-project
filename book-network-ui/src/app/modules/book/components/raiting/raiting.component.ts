import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-raiting',
  imports: [],
  templateUrl: './raiting.component.html',
  styleUrl: './raiting.component.scss'
})
export class RaitingComponent {


  @Input() rating :number = 0 ;
  maxRating :number = 5

  get fullStars():number{
    return Math.floor(this.rating)
  }

  get halfStar():boolean {
    return this.rating % 1 !== 0;
  }

  get emptStars():number{
    return this.maxRating - Math.ceil(this.rating);
  }

}
