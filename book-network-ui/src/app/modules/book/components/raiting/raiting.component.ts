import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-raiting',
  imports: [CommonModule],
  templateUrl: './raiting.component.html',
  styleUrl: './raiting.component.scss'
})
export class RaitingComponent {


  @Input() rating :number = 0 ;
  maxRating :number = 5

  get fullStars():number{
    return Math.floor(this.rating)
  }

  get hasHalfStar():boolean {
    return this.rating % 1 !== 0;
  }

  get emptStars():number{
    return this.maxRating - Math.ceil(this.rating);
  }

}
