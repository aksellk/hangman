# hangman

Aksel Langø Karlsen

1. november 2016

### Om prosjektet
Prosjektet består av et Hangman-spill utviklet i android.

Prosjektet er laget av Aksel Langø Karlsen for emnet IFUD1042-A 16H
Applikasjonsutvikling for Android.

### Utvikling
Løsningen er programmert i Android Studio versjon 2.2 på en maskin som bruker
operativsystemet Ubuntu 16.04.

Applikasjonens minimum SDK-versjon er 21.

Applikasjonen er kompilert i SDK-versjon 24 og har target-SDK-versjon 24.

Applikasjonen er testet på to emulatorer:

En Nexus 5X med følgende egenskaper:
• API: 22
• CPU/ABI: Google APIs Intel Atom (x86_64)
• SD Kort: 100M

En Galaxy Nexus med følgende egenskaper:
• API: 22
• CPU/ABI: Google APIs Intel Atom (x86)
• SD Kort: 100M

### Tilgjengelighet og forrutsetninger
For å kunne kjøre applikasjonen må man ha en android-mobil eller emulator med
SDK-versjon 21 eller høyere.

### Kjøring av prosjektet
Ved å gjøre følgende steg kan man kjøre spillet fra Android Studio:
1. Åpne prosjektet i Android Studio.
2. Naviger til feltet Run ’app’ i menyen øverst i Android Studio eller klikk
knappen Run ’app’.
Etter å ha trykket Run ’app’ får man opp en dialog der man må velge
emulator. Velg en og klikk knappen OK. Dette vil starte applikasjonen på
valgt emulator. Hvis Emulatoren ikke allerede er startet må den startes opp
først.

### Om løsningen

####Spillets Hovedflyt
Løsningen er kodet slik at det brukes en hoved-aktivitet som starter en
spill-aktivitet hver gang brukereren vil starte et nytt spill. Hovedaktiviteten heter
MainActivity.java og Spill-aktiviteten heter GameActivity.java. MainActivity er
tilknyttet layout-filen activity_main.xml, og GameActivity er tilknyttet
layout-filen activity_game.xml.

#####Hovedaktivitet og hoved-layout-fil
Layout-filen activity_game.xml inneholder en knapp. Brukeren trykker på denne
knappen for hver gang et nytt spill startes. For denne knappen har MainActivity
implementert en knappelytter. Knappelytteren starter spill-aktiviteten
GameActivity.

#####Spill-aktivitet og spill-layout-fil
Aktiviteten GameAvtivity kalles for hver gang en ny spill-runde startes og
avsluttes når en spill-runde avsluttes. En spill-runde består av å gjette ett ord.
For å gjette flere ord går må man starte nye spill via hovedmenyen.
Layout-filen activity_game.xml har tre hovedkomponenter plassert under
hverandre i økende rekkefølge:
1. En LinearLayout som skal inneholde TextView for hvert tegn i ordet som
skal gjettes.
2. Et ImageView som skal inneholde bilder for hangman-tegningen.
3. En TableLayout som inneholder et TextView for hver bokstav i alfabetet.
Disse TextViewene brukes som knapper for bokstavene som brukeren kan
gjette at er med i løsningsordet.
GameActivity starter med å hente inn en liste med ord fra filen strings.xml.
Deretter kalles metoden newWord() som velger et tilfeldig ord blant listen med
ord. Ordet splittes så opp i strenger som inneholder et tegn. For å holde orden på
tegnene brukes en domeneklasse som heter Letter.java. Letter.java har tre
klassevariabler; Strengen med tegnet/bokstaven, en tall-id og et TextView. Det
opprettes et nytt Letter-objekt for alle tegnene som utgjør ordet. Alle
3Letter-objektene legges så i en lise. For alle letter objektene i lista legges
TextViewene til i en LinearLayout ved hjelp av addView(TextView).
Metoden onLetterClicked() i GameActivity er en knappelytter for TextViewene
brukeren bruker til å gjette bokstaver i løsningsordet. Når onLetterClicked()
kalles skjer følgende:
1. tegnet i det valgte TextViewet hentes ut og plasseres i en streng.
2. For hvert Letter-objekt i listen med Letter-objekter undersøkes det om det
valgte tegnet er lik et av tegnet i Letter-objektet. Hvis match inkrementeres
antall funnede tegn av løsningsordet og TextViewet i LinearLayouten med
TextViewer for tegnene i løsningsordet oppdateres med det funnede tegnet.
3. Deretter undersøkes det om totalt antall funnede tegn er lik lengden på
løsningsordet. Hvis totalt antall funnede tegn er lik antall tegn i løsningen
avsluttes spillet. Hvis antall funnede tegn er 0 oppdatateres
Hangman-tegningen med en ny tegning der hangman-figuren har fått en ny
kroppsdel tegnet. Hvis antall funnede tegn er 0 dekrementers også antall
sjanser. Hvis antall sjanser er 0 som betyr at hangman er ferdig tegnet
avsluttes også spillet.
Når spillet avsluttes kalles metoden showDialog(boolean status) i GameActivity.
showDialog() oppretter en ny dialog. Denne dialogen har et egen Layout-fil. I
Layout filen er det to TextView og en knapp. Det første TextViewet inneholder
en status som sier om spilleren har vunnet eller tapt. Den settes avhengig av
boolean variabelen som sendes i parameter til showDialog()-metoden. Det andre
TextViewet inneholder løsningsordet. Når knappen trykkes fjernes dialogen ved
hjelp av dialog.dismiss(). Deretter Avsluttes aktiviteten og applikasjonen går
tilbake til hovedakviviteten MainActivity.

#####Hjelpefunksjon
For å bedre brukervennligheten er det laget en hjelpefunksjon. Hjelpefunksjonen
inneholder informasjon om hvordan spillet fungerer. Hjelpefunksjonen er
implementert ved å bruke et MenuItem i ActionBaren. I både MainActivity og
GameActivity er metoden onOptionsItemSelected() implementert som lytter på
om dette meny-valget er valgt. Hvis det er tilfelle startes aktiviteten HelpActivity.
Til HelpActivity er det knyttet en layout-fil som heter activity_help.xml som
inneholder et TextView og en knapp. TextViewet inneholder teksten med
informasjon om spillet. HelpActivity inneholder en knappelytter som lytter på
knappen og avslutter aktiviteten når knappen trykkes.

#####Internasjonalisering
I denne Applikasjonen er det lagt støtte for to språk; norsk og engelsk. Resursene
på norsk og engelsk finnes i henholdsvis filene en/strings.xml og nb/strings.xml.
Avhengig av om valgt språk er norsk eller engelsk settes tekstene i layoutfilene
med norsk eller engelsk tekst fra strings.xml-filene
Løsningen baserer seg på å bruke gjeldende lokale. I MainActivity sørges det for
å laste inn lokale og å endre lokale.

#####Endre lokale
I MainAcitivity er det lagt til en MenuItem for instillinger i ActionBaren. I
metoden onOptionsItemSelected(MenuItem item) vil det når dette menyvalget
starte aktiviteten LocaleActivity. LocaleActivity inneholder metoden onCreate().
Når onCreate()-metoden i LocaleActivity kalles når aktiviteten opprettes vil den
starte fragmentet LocaleFragment. I fragmentet LocaleFragment finnes kun
metoden onCreate(). Metoden onCreate() i LocaleFragment kaller
addPreferencesFromResource(R.xml.preferences). Dette metodekallet sørger for å
legge til en en xml-fil som heter preferences.xml og inneholder et
PreferenceScreen-objekt. PreferenceScreen-objektet inneholder to instanser av
CheckBoxPreference. Den ene er for å velge norsk lokale og den andre er for å
velge engelsk lokale. CheckBoxPreferencene har attributtene key, defaultValue og
title. Title er navnet som vises, defaultValue er en boolean som viser
default-verdien til CheckBoxPrenferencen. Key brukes til å identifisere
CheckBoxPreference-instansen.

#####Laste inn lokale
For å laste inn lokale kalles metoden loadLocalePreferences() i MainActivity. Den
kalles i metoden onActivityResult(), altså når en aktivitet er avsluttet.
loadLocalePreferences() oppretter et SharedPreference-objekt ved å bruke
PreferenceManager.getDefaultSharedPreferences(this). Dette returnerer
PreferenceScreen-objektet fra preferences.xml. Vi henter verdiene fra
CheckBoxPreferencene med sharedPreferences.getBoolean(). Der første argument
er nøkkel norsk eller engelsk. Deretter settes lokale basert på hvilken av
Checkoxene som er satt og MainActivity startes på nytt.

#####Avslutte applikasjonen
Avslutting av applikasjonen er håndtert for slik at man i alle deler av spillet har
mulighet til å avslutte. Dette er implementert ved å bruke et MenuItem med
navn avslutt i ActionBar. ActionBar med dette MenuItemet er implementert i
både MainActivity, GameActivity og HelpActivity. Alle tre aktivitets-klassene
har overskrevet metodene onOptionsCreateMenu() og onOptionsItemSelected(). I
metoden onOptionsItemSelected() undersøkes det om MenuItemet avslutt er
trykket, og hvis det er trykket kalles finishAffinity(). Metoden finishAffinity()
sørger for å avslutte gjeldende aktivitet og alle foreldre-aktiviteter til gjeldende
5aktivitet. Derfor avsluttes applikasjonen når finishAffinity()-metoden kalles i
disse aktivitetene.

