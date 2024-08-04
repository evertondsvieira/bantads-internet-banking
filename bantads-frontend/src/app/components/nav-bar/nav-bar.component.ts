import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { UserServiceId } from '../../services/auth/userId.service';
import { Subscription, interval } from 'rxjs';

interface INavItems {
  name: string
  icon: string
  link: string
  role: 'CLIENT' | 'MANAGER' | 'ADMIN' | 'ALL'
}

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit {
  userId: string | null = null;
  userLogin: string | null = null;
  userRole: string | null = null;
  navItems: INavItems[] = [];

  private userIdSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private router: Router,
    private userService: UserServiceId
  ) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.userLogin = this.authService.loggedUser?.email ?? null;
    this.userRole = this.authService.loggedUser?.profile ?? null;

    if (this.userId || this.userLogin || this.userRole) {
      this.updateNavItems()
    } else {
      this.userIdSubscription = interval(1000).subscribe(() => {
        this.userService.getUserId().subscribe(id => {
          if (id) {
            this.userId = id;
            this.updateNavItems();
            this.userIdSubscription.unsubscribe()
          }
        });
      });
    }
  }

  updateNavItems(): void {
    this.navItems = [
      { name: 'Início', icon: 'home', link: 'home', role: 'CLIENT' },
      { name: 'Saldo Atual', icon: 'account_balance', link: `current/balance/${this.userId}`, role: 'CLIENT' },
      { name: 'Depósito', icon: 'arrow_upward', link: `deposit/${this.userId}`, role: 'CLIENT' },
      { name: 'Extrato', icon: 'description', link: `statement/${this.userId}`, role: 'CLIENT' },
      { name: 'Transferência', icon: 'swap_horiz', link: `transfer/${this.userId}`, role: 'CLIENT' },
      { name: 'Atualizar Perfil', icon: 'person', link: `update/profile/${this.userId}`, role: 'CLIENT' },
      { name: 'Retirada', icon: 'arrow_downward', link: `withdrawl/${this.userId}`, role: 'CLIENT' },
      { name: 'Início do Gerente', icon: 'business', link: 'manager/home', role: 'MANAGER' },
      { name: 'Consultar Todos os Clientes', icon: 'people', link: 'manager/consult/all', role: 'MANAGER' },
      { name: 'Consultar Clientes', icon: 'person', link: 'manager/consult/customers', role: 'MANAGER' },
      { name: 'Consultar Top', icon: 'trending_up', link: 'manager/consult/top', role: 'MANAGER' },
      { name: 'Início do Administrador', icon: 'admin_panel_settings', link: 'admin/home', role: 'ADMIN' },
      { name: 'Relatório de Clientes', icon: 'assessment', link: 'admin/customer/report', role: 'ADMIN' },
      { name: 'Adicionar Gerente', icon: 'person_add', link: 'admin/add/manager', role: 'ADMIN' },
      { name: 'Listar Gerentes', icon: 'list_alt', link: 'admin/list/manager', role: 'ADMIN' },
      { name: 'Deslogar', icon: 'logout', link: '/login', role: 'ALL' },
    ];
  }

  getFilteredNavItems(): INavItems[] {
    const userRole = this.authService.loggedUser?.profile;

    if (!userRole) return []

    if (userRole === 'ADMIN' || userRole === 'MANAGER' || userRole === 'CLIENT') {
      return this.navItems.filter(item => item.role === userRole || item.role === 'ALL')
    }

    return []
  }

  logout(): void {
    this.authService.logout()
    sessionStorage.removeItem('loggedUser')
    this.router.navigate(['/login'])
  }
}
