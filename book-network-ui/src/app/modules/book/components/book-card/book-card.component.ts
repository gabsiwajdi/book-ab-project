import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {NgIf} from '@angular/common';
import {RaitingComponent} from '../raiting/raiting.component';

@Component({
  selector: 'app-book-card',
  imports: [
    NgIf,
    RaitingComponent
  ],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {

  private _book :BookResponse = {} ;
  private _bookCover : string | undefined ;
  private _manege = false;



  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  get bookCover(): string | undefined {
    if(this._book.cover){
      return 'data:image/jpg;base64, ' +this._bookCover ;
    }
    return this._bookCover;
  }

  get manege(): boolean {
    return this._manege;
  }

  set manege(value: boolean) {
    this._manege = value;
  }

  @Output() private share :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private archive :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private addToWaitigList :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private borrow :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private edit :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  @Output() private details :EventEmitter<BookResponse> = new EventEmitter<BookResponse>();


  onShowDetails() {
this.details.emit(this._book)
  }

  onBorrow() {
    this.borrow.emit(this._book)

  }

  onAddToWhaitingList() {
    this.addToWaitigList.emit(this._book)

  }

  onEdit() {
    this.edit.emit(this._book)

  }

  onShared() {
    this.share.emit(this._book)

  }

  onArchive() {
    this.archive.emit(this._book)

  }
}
