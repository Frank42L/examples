// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');


class MfloCallToAction extends HTMLElement {
    constructor() { 
        super();
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.shadow.innerHTML = `
                <style>@import url("assets/css/style.css");</style>
                <style>@import url("assets/css/style_mflo.css");</style>
                <style>@import url("assets/css/style_mflo_simulation.css");</style>
                <div class="mflo-call-to-action">
                    <a href="${this.href}"><slot>[CTA_TEXT]</slot></a>
                </div>
            `;
        }
    }

    set href(hrefContent) { 
        this.setAttribute('href', hrefContent);
    }
    
    get href() { 
        return this.getAttribute('href');
    }

}

customElements.define('mflo-call-to-action', MfloCallToAction);


