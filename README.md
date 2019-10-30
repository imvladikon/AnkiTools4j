# AnkiTools4j
AnkiTools for Java


### Basic use
``` java
Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)");
test.addItem("להעריך", "оценить, рассчитывать");
test.addItem("להרהר", "размышлять, медитировать");
test.CreateApkgFile(_PATH_FOR_ANKI_FILE_);
```

### SetFields
``` java
    Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
    test.setFields("Hebrew", "English", "Russian");
    test.addItem("לְהַנִּיחַ", "to assume", "полагать");
    test.addItem("להעריך", "to estimate, value; appreciate", "ценить");
    test.addItem("מחקר", "research", "исследование");
    test.addItem("לדמיין", "to imagine", "воображать");
    test.addItem("דמיון יוצר", "creative imagination", "творческое воображение");
    test.addItem("סביר יותר", "likely", "наиболее вероятно");
    test.addItem("דמות", "character, image", "образ");
    test.addItem("סבירות נמוכה בהצלחה",	"low probability of success",
			    	"низкая вероятность успеха");
    test.createApkgFile(_PATH_FOR_ANKI_FILE_);

```
