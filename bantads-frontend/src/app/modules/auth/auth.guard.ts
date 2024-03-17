import { CanActivateFn, Router, ActivatedRouteSnapshot, RouterStateSnapshot,UrlTree } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree => {
  const usuarioLogado = inject(AuthService).loggedUser;
  let url = state.url;
  let router = inject(Router);

  if(usuarioLogado){
    if(route.data?.['role'] && route.data?.['role'].indexOf(usuarioLogado.profile)=== -1){
      router.navigate(['/login'], 
                      {queryParams: { error: `Proibido acesso a ${url}.`} });
      return false;    
    }
    //Em outros casos permitir o acesso
    return true;
  }

  router.navigate(['/login'],
                 { queryParams: {error: `É necessário efetuar o Login antes de acessar ${url}`} }); 
  return false;
};
