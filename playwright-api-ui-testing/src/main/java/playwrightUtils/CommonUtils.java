package playwrightUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CommonUtils {

    public static Locator getLocator(Page page, Field field) {

        String locatorType = field.getLocatorType();
        String locatorValue = field.getLocatorValue();

        if (locatorType == null || locatorType.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing locatorType for field: " + field.getName());
        }
        if (locatorValue == null || locatorValue.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing locatorValue for field: " + field.getName());
        }

        switch (locatorType.toLowerCase()) {
        case "id":
            return page.locator("#" + locatorValue);
        case "name":
            return page.locator("[name='" + locatorValue + "']");
        case "css":
        case "cssselector":
            return page.locator(locatorValue);
        case "xpath":
            return page.locator("xpath=" + locatorValue);
        case "class":
        case "classname":
            return page.locator("." + locatorValue);
        case "tag":
        case "tagname":
            return page.locator(locatorValue);
        case "linktext":
            return page.getByText(locatorValue, new Page.GetByTextOptions().setExact(true));
        case "partiallinktext":
            return page.getByText(locatorValue);
        default:
            throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
    }

    public static void fillFormData(List<Field> fields, Page page, String screenshotPath) {

        for (Field field : fields) {
            try {
                Locator locator = getLocator(page, field);
                String type = field.getType().toLowerCase();
                String value = field.getValue();

                switch (type) {

                case "text":
                case "number":
                case "email":
                case "password":
                case "textarea":
                    locator.fill(value);
                    break;

                case "checkbox":
                case "radio":
                    if (!locator.isChecked()) {
                        locator.check();
                    }
                    break;

                case "select":
                    locator.selectOption(new SelectOption().setLabel(value));
                    break;

                case "file":
                    locator.setInputFiles(Paths.get(value));
                    break;

                case "clickbutton":
                    locator.click();
                    break;

                default:
                    System.err.println("Unsupported field type: " + type);
                }

            } catch (Exception e) {
                System.err.println("Failed to process field: " + field.getName() + " → " + e.getMessage());
            }
        }

        // Take Screenshot
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
    }

    public static List<Field> loadFieldsFromJson(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // without TypeReference it is losing type and becomes List<Object/Map>, it provides generic type info..
        return mapper.readValue(new File(jsonFilePath), new TypeReference<List<Field>>() {
        });
    }

    public static void processFieldsByNameAndClick(List<Field> fields, String nameToProcess, Page page) {
        for (Field field : fields) {

            if (nameToProcess.equals(field.getName())) {

                // Resolve selector based on locatorType & locatorValue
                Locator locator = getLocator(page, field);

                // Equivalent to: waitForElementToBeClickable + click
                locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

                locator.click();
            }
        }
    }

    public static String processFieldsByNameAndGetElementValue(List<Field> fields, String nameToProcess, String fieldToReturn) {

        for (Field field : fields) {
            if (nameToProcess.equals(field.getName())) {

                switch (fieldToReturn) {
                case "type":
                    return field.getType();
                case "value":
                    return field.getValue();
                case "locatorType":
                    return field.getLocatorType();
                case "locatorValue":
                    return field.getLocatorValue();
                case "name":
                    return field.getName();
                default:
                    return null;
                }
            }
        }
        return null;
    }

    public static boolean isCampaignPresentInTab(List<Locator> campaignCards, String campaignName, String tabStatus) {

        boolean isFound = false;

        for (Locator card : campaignCards) {
            if (card.innerText().contains(campaignName)) {
                isFound = true;
                break;
            }
        }

        if (isFound) {
            System.out.println("Campaign: " + campaignName + " is found in the " + tabStatus + " tab.");
        } else {
            System.out.println("Campaign: " + campaignName + " is NOT found in the " + tabStatus + " tab.");
        }

        return isFound;
    }

    public static void clickLatestVersionTasks(Page page, String formTitle) throws InterruptedException {
        Locator cards = page.locator("//h5[normalize-space()='" + formTitle + "']/ancestor::div[contains(@class,'card')]");

        int cardCount = cards.count();

        // “not set yet.” When a real highest value is found, it overwrites the -1.
        double highestVersion = -1;
        Locator latestCard = null;

        for (int i = 0; i < cardCount; i++) {
            Locator currentCard = cards.nth(i);

            // Extract version text like "v7.0"
            String versionText = currentCard.locator("text=/v[0-9]+\\.[0-9]+/").innerText().trim();

            // Remove 'v' and storing nbr
            double version = Double.parseDouble(versionText.substring(1));

            // Comparing the versions
            if (version > highestVersion) {
                highestVersion = version;
                latestCard = currentCard;
            }
        }

        // Click eye icon or Tasks in the latest card
        if (latestCard != null) {
            Thread.sleep(3000);
            latestCard.locator("i[class*='icon-eye']").first().click();
        }
    }

    public static void clickEyeIconForForm(Page page, String formTitle) {
        Locator card = page.locator("//h5[normalize-space()='" + formTitle + "']/ancestor::div[contains(@class,'card')]");
        card.locator("i[class*='icon-eye']").click();
    }

    public static void adminAction(Page page, String title, String remarks, boolean approve, String screenshotPath) throws InterruptedException {
        // --- Go to Other Actions and un-claim ---
        page.click("text=/Other Actions.*/");
        Page.LocatorOptions otherActionOptions = new Page.LocatorOptions().setHasText(title);
        Locator otherActionRow = page.locator("tr", otherActionOptions);
        // click the claim icon inside this row (un-claim)
        otherActionRow.locator("i.icon-user-check").click();

        // --- Go to My Actions and view details ---
        page.click("text=/My Actions.*/");
        Page.LocatorOptions myActionOptions = new Page.LocatorOptions().setHasText(title);
        Locator myActionRow = page.locator("tr", myActionOptions);
        myActionRow.locator("i.icon-eye").click();
        Thread.sleep(2000);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));

        // --- Approve or Reject ---
        if (approve) {
            page.click("text='Approve'");
        } else {
            page.fill("input[name='data[remarks]']", remarks);
            page.click("text='Reject'");
        }
    }

}
