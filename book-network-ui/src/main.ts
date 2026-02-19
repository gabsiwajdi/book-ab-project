import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {provideRouter} from '@angular/router';
import {routes} from './app/app.routes';
import {httpTokenInterceptor} from './app/services/interceptor/http-token.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(
      withInterceptors([httpTokenInterceptor])
    ),
    provideRouter(routes),
  ]
})
  .catch((err) => console.error(err));

