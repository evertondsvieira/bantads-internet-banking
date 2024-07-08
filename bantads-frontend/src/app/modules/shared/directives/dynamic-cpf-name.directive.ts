import { Directive, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[dynamicCpfName]'
})
export class DynamicCpfNameDirective {

  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event'])
  onInput(event: any) {
    const input = event.target;
    const value = input.value;

    if (/^\D+$/.test(value)) {
      // Se a entrada é apenas números, aplique a máscara de CPF
      input.value = this.applyCpfMask(value);
    } else {
      // Se a entrada contém letras, remova qualquer máscara
      input.value = value;
    }
  }

  private applyCpfMask(value: string): string {
    value = value.replace(/\D/g, ''); // Remove qualquer caractere não numérico
    if (value.length > 11) {
      value = value.substring(0, 11);
    }
    const parts = [];
    if (value.length > 3) {
      parts.push(value.substring(0, 3));
      value = value.substring(3);
    }
    if (value.length > 3) {
      parts.push(value.substring(0, 3));
      value = value.substring(3);
    }
    if (value.length > 3) {
      parts.push(value.substring(0, 3));
      value = value.substring(3);
    }
    if (value.length > 2) {
      parts.push(value.substring(0, 2));
      value = value.substring(2);
    }
    return parts.join('.');
  }
}
