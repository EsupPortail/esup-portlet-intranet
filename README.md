# esup-portlet-intranet

esup-portlet-intranet is part of [esup-ecm](https://www.esup-portail.org/pages/viewpage.action?pageId=82870299), a web interface for [Nuxeo](see www.nuxeo.com).

It is a portlet client for [uPortal](https://github.com/EsupPortail/esup-uportal.git).

## Installation

1. Download last version of portlet war from [esup maven repository](https://mvn.esup-portail.org/content/repositories/releases/org/esupportail/esup-portlet-intranet-web-springmvc-portlet/).
2. From uPortal launch ant portlet.deploy -DportletApp=/path/to/war/portlet.war.
3. As uPortal administrator register this new portlet.
4. Don't forget editing preferences (see bellow).

## Preferences
+ nuxeoHost(Nuxeo URL) : Editable, ex:) http://localhost:8080/nuxeo
+ intranetPath(The Nuxeo path used as root content) : Editable, ex:) /default-domain/workspaces/foo
+ nuxeoPortalAuthSecret(Secret used by Nuxeo [portal-sso](http://www.esup-portail.org/pages/viewpage.action?pageId=201097232) authentication layer) : No Editable, ex:) ITJDrjUWLGZ1fNSil795

============
"Editable" is useful uniquely if uPortal administrator add edit capability to the portlet.
In this case, if uPortal administrator configure a preference as read-only=false, this preference will be editable with the portlet edit view.
