// import { html, render } from './lit-html.js';

class StzhMkWidgetCanvas extends HTMLElement { 

    constructor() { 
        super();
        var shadow = this.attachShadow({mode: 'open'});
        
        if (this.hasAttribute('title')) {
            this.widgetTitle = this.getAttribute('title');
        } else {
            this.widgetTitle = '<NO TITLE>';
        }

        if (this.hasAttribute('subTitle')) {
            this.subTitle = this.getAttribute('subTitle');
        } else {
            this.subTitle = '<NO SUB-TITLE>';
        }

        this.messageCounter = 3;
        this.items = [1,2,3];

        this.createTemplate(shadow);
    }

    createTemplate(shadow) {
        var appDashboardItem = document.createElement('app-dashboard-item');
        appDashboardItem.setAttribute('class', 'mod_dashboardcard');

        var appWidgetContainer = document.createElement('app-widget-container');
        appWidgetContainer.setAttribute('class', 'dashboardcard');
        appDashboardItem.appendChild(appWidgetContainer);

        var appWidgetContainer1 = document.createElement('div');
        appWidgetContainer1.setAttribute('class', 'dashboardcard');
        appWidgetContainer.appendChild(appWidgetContainer1);

        var appWidgetHeader = document.createElement('div');
        appWidgetHeader.setAttribute('class', 'dashboardcardheader');
        appWidgetContainer1.appendChild(appWidgetHeader);

        var appWidgetHeaderText = document.createElement('div');
        appWidgetHeaderText.setAttribute('class', 'dashboardcardheadertext');
        appWidgetHeader.appendChild(appWidgetHeaderText);
        
        var appWidgetTitle = document.createElement('h2');
        appWidgetTitle.setAttribute('class', 'title');
        appWidgetTitle.textContent = this.widgetTitle;
        appWidgetHeaderText.appendChild(appWidgetTitle);

        var appWidgetSubTitle = document.createElement('p');
        appWidgetSubTitle.setAttribute('class', 'subtitle');
        appWidgetSubTitle.textContent = this.subTitle;
        appWidgetHeaderText.appendChild(appWidgetSubTitle);

        var appWidgetTriggerIcon = document.createElement('div');
        appWidgetTriggerIcon.setAttribute('class', 'trigger_icon');
        appWidgetHeader.appendChild(appWidgetTriggerIcon);

        var appWidgetContent = document.createElement('div');
        appWidgetContent.setAttribute('class', 'card_body');
        appWidgetContent.textContent = this.textContent;
        appWidgetContainer1.appendChild(appWidgetContent);



        var wrapper = document.createElement('div');
        wrapper.setAttribute('class', 'stzhMkWidgetTitle');
        wrapper.textContent = this.widgetTitle;
        wrapper.setAttribute('class', 'stzhMkWidgetCanvas');
        
        var countMarker = document.createElement('div');
        countMarker.setAttribute('class', 'stzhMkCountMarker');
        countMarker.setAttribute('tabIndex', 0);

        var info = document.createElement('div');
        info.setAttribute('class', 'stzhMkInfo');
        info.textContent = this.subTitle;

        var imgUrl = 'img/info_icon.png';
        var img = document.createElement('img');
        img.src = imgUrl;
        countMarker.appendChild(img);

        var inhalt = document.createElement('div');
        inhalt.setAttribute('class', 'stzhMkWidgetInhalt');
        inhalt.textContent = this.textContent;

        var style = document.createElement('style');
        style.textContent = '@import url("assets/css/rwrd_simulation.css");';
        shadow.appendChild(style);
        shadow.appendChild(appDashboardItem);
        shadow.appendChild(wrapper);
        wrapper.appendChild(countMarker);
        wrapper.appendChild(info);
        wrapper.appendChild(inhalt);

    }
}

customElements.define('stzh-mk-widget-canvas', StzhMkWidgetCanvas);



