import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BookService} from '../../../../services/services/book.service';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {CommonModule} from '@angular/common';
import {BookCardComponent} from '../../components/book-card/book-card.component';

@Component({
  selector: 'app-book-list',
  imports: [CommonModule, BookCardComponent],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{

  bookResponse: PageResponseBookResponse = {};
  page = 0;
   size= 5;
  pages: any = [];

  constructor(
    private bookService : BookService,
    private router : Router

  ) {
  }

  async ngOnInit() {
    this.findAllBooks();
  }


  private async findAllBooks() {
    try {
      const books = await this.bookService.findAllBooks({
        page: this.page,
        size: this.size
      });
      this.bookResponse = books;
      // console.log("Books loaded:", books);
      console.log(" bookResponse" ,this.bookResponse);

    } catch (error) {
      console.error('Erreur lors du chargement des livres', error);
    }

  }

  goToFirstPage(){}
  goToPreviousPage(){}
  gotToPage(index:number){}
  goToNextPage(){}
  goToLastPage(){}

  }
