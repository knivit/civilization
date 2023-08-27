package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Format;

public class GetCivilizationInfo {

    public static GetCivilizationInfo newInstance() {
        return new GetCivilizationInfo();
    }

    public StringBuilder getContent(Civilization civilization) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$civilizationName</td></tr>
                <tr><td><img src='$civilizationImage'/></td></tr>
            </table>
            """,

            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }
}
