import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';

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
export class NavBarComponent {
  constructor(private authService: AuthService, private router: Router) {}

  navItems: INavItems[] = [
    { name: 'Início', icon: 'home', link: 'home', role: 'CLIENT' },
    { name: 'Saldo Atual', icon: 'account_balance', link: 'current/balance', role: 'CLIENT' },
    { name: 'Depósito', icon: 'arrow_upward', link: 'deposit', role: 'CLIENT' },
    { name: 'Extrato', icon: 'description', link: 'statement', role: 'CLIENT' },
    { name: 'Transferência', icon: 'swap_horiz', link: 'transfer', role: 'CLIENT' },
    { name: 'Atualizar Perfil', icon: 'person', link: 'update/profile', role: 'CLIENT' },
    { name: 'Retirada', icon: 'arrow_downward', link: 'withdrawl', role: 'CLIENT' },
    { name: 'Início do Gerente', icon: 'business', link: 'manager/home', role: 'MANAGER' },
    { name: 'Consultar Todos os Clientes', icon: 'people', link: 'manager/consult/all', role: 'MANAGER' },
    { name: 'Consultar Clientes', icon: 'person', link: 'manager/consult/customers', role: 'MANAGER' },
    { name: 'Consultar Top', icon: 'trending_up', link: 'manager/consult/top', role: 'MANAGER' },
    { name: 'Aprovar', icon: 'thumb_up', link: 'manager/approve', role: 'MANAGER' },
    { name: 'Rejeitar', icon: 'thumb_down', link: 'manager/reject', role: 'MANAGER' },
    { name: 'Início do Administrador', icon: 'admin_panel_settings', link: 'admin/home', role: 'ADMIN' },
    { name: 'Relatório de Clientes', icon: 'assessment', link: 'admin/customer/report', role: 'ADMIN' },
    { name: 'Adicionar Gerente', icon: 'person_add', link: 'admin/add/manager', role: 'ADMIN' },
    { name: 'Remover Gerente', icon: 'person_remove', link: 'admin/remove/manager', role: 'ADMIN' },
    { name: 'Listar Gerentes', icon: 'list_alt', link: 'admin/list/manager', role: 'ADMIN' },
    { name: 'Atualizar Gerente', icon: 'settings', link: 'admin/update/manager', role: 'ADMIN' },
    { name: 'Deslogar', icon: 'logout', link: '/login', role: 'ALL' },
  ]
  
  getFilteredNavItems(): INavItems[] {
    const userRole = this.authService.loggedUser?.profile;

    if (!userRole) return []

    if (userRole === 'ADMIN' || userRole === 'MANAGER' || userRole === 'CLIENT') {
      return this.navItems.filter(item => item.role === userRole || item.role === 'ALL');
    }

    return []
  }

  logout(): void {
    sessionStorage.removeItem('loggedUser')
    this.router.navigate(['/login'])
  }
}
