package nz.co.seclib.dbroker.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import nz.co.seclib.dbroker.R

class PrivacyPolicy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        tvPrivacyPolicy.text = Html.fromHtml("<h1>Privacy Policy</h1>\n" +
                "\n" +
                "<p>Last updated: May 24, 2020</p>\n" +
                "\n" +
                "<p>This Privacy Policy describes Our policies and procedures on the collection, use and disclosure of Your information when You use the Service and tells You about Your privacy rights and how the law protects You.</p>\n" +
                "\n" +
                "<p>We use Your Personal data to provide and improve the Service. By using the Service, You agree to the collection and use of information in accordance with this Privacy Policy. This Privacy Policy is maintained by the <a href=\"https://www.freeprivacypolicy.com/free-privacy-policy-generator/\">Free Privacy Policy Generator</a>.</p>\n" +
                "\n" +
                "<h1>Interpretation and Definitions</h1>\n" +
                "<h2>Interpretation</h2>\n" +
                "<p>The words of which the initial letter is capitalized have meanings defined under the following conditions.</p>\n" +
                "<p>The following definitions shall have the same meaning regardless of whether they appear in singular or in plural.</p>\n" +
                "\n" +
                "<h2>Definitions</h2>\n" +
                "<p>For the purposes of this Privacy Policy:</p>\n" +
                "<ul>\n" +
                "    <li>\n" +
                "        <p><strong>You</strong> means the individual accessing or using the Service, or the company, or other legal entity on behalf of which such individual is accessing or using the Service, as applicable.</p>\n" +
                "          </li>\n" +
                "    <li>\n" +
                "        <p><strong>Company</strong> (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this Agreement) refers to DBroker.</p>\n" +
                "          </li>\n" +
                "      <li><strong>Affiliate</strong> means an entity that controls, is controlled by or is under common control with a party, where \"control\" means ownership of 50% or more of the shares, equity interest or other securities entitled to vote for election of directors or other managing authority.</li>\n" +
                "    <li><strong>Account</strong> means a unique account created for You to access our Service or parts of our Service.</li>\n" +
                "  <li><strong>Website</strong> refers to DBroker, accessible from https://sites.google.com/view/dbroker/home</li>        <li><strong>Service</strong> refers to the Website.</li>\n" +
                "      <li><strong>Country</strong> refers to: New Zealand</li>\n" +
                "    <li>\n" +
                "        <p><strong>Service Provider</strong> means any natural or legal person who processes the data on behalf of the Company. It refers to third-party companies or individuals employed by the Company to facilitate the Service, to provide the Service on behalf of the Company, to perform services related to the Service or to assist the Company in analyzing how the Service is used.</p>\n" +
                "          </li>\n" +
                "    <li><strong>Third-party Social Media Service</strong> refers to any website or any social network website through which a User can log in or create an account to use the Service.</li>\n" +
                "      <li>\n" +
                "        <p><strong>Personal Data</strong> is any information that relates to an identified or identifiable individual.</p>\n" +
                "                </li>\n" +
                "  <li><strong>Cookies</strong> are small files that are placed on Your computer, mobile device or any other device by a website, containing the details of Your browsing history on that website among its many uses.</li>  <li><strong>Device</strong> means any device that can access the Service such as a computer, a cellphone or a digital tablet.</li>\n" +
                "    <li><strong>Usage Data</strong> refers to data collected automatically, either generated by the use of the Service or from the Service infrastructure itself (for example, the duration of a page visit).</li>\n" +
                "      </ul>\n" +
                "\n" +
                "<h1>Collecting and Using Your Personal Data</h1>\n" +
                "<h2>Types of Data Collected</h2>\n" +
                "\n" +
                "<h3>Personal Data</h3>\n" +
                "<p>While using Our Service, We may ask You to provide Us with certain personally identifiable information that can be used to contact or identify You. Personally identifiable information may include, but is not limited to:</p>\n" +
                "<ul>\n" +
                "              <li>Usage Data</li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "<h3>Usage Data</h3>\n" +
                "<p>Usage Data is collected automatically when using the Service.</p>\n" +
                "<p>Usage Data may include information such as Your Device's Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that You visit, the time and date of Your visit, the time spent on those pages, unique device identifiers and other diagnostic data.</p>\n" +
                "<p>When You access the Service by or through a mobile device, We may collect certain information automatically, including, but not limited to, the type of mobile device You use, Your mobile device unique ID, the IP address of Your mobile device, Your mobile operating system, the type of mobile Internet browser You use, unique device identifiers and other diagnostic data.</p>\n" +
                "<p>We may also collect information that Your browser sends whenever You visit our Service or when You access the Service by or through a mobile device.</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <h3>Tracking Technologies and Cookies</h3>\n" +
                "    <p>We use Cookies and similar tracking technologies to track the activity on Our Service and store certain information. Tracking technologies used are beacons, tags, and scripts to collect and track information and to improve and analyze Our Service.</p>\n" +
                "    <p>You can instruct Your browser to refuse all Cookies or to indicate when a Cookie is being sent. However, if You do not accept Cookies, You may not be able to use some parts of our Service.</p>\n" +
                "    <p>Cookies can be \"Persistent\" or \"Session\" Cookies. Persistent Cookies remain on your personal computer or mobile device when You go offline, while Session Cookies are deleted as soon as You close your web browser. Learn more about cookies here: <a href=\"https://www.freeprivacypolicy.com/blog/cookies/\">Cookies: What Do They Do?</a></p>\n" +
                "    <p>We use both session and persistent Cookies for the purposes set out below:</p>\n" +
                "    <ul>\n" +
                "        <li>\n" +
                "            <p><strong>Necessary / Essential Cookies</strong>\n" +
                "            <p>Type: Session Cookies</p>\n" +
                "            <p>Administered by: Us</p>\n" +
                "            <p>Purpose: These Cookies are essential to provide You with services available through the Website and to enable You to use some of its features. They help to authenticate users and prevent fraudulent use of user accounts. Without these Cookies, the services that You have asked for cannot be provided, and We only use these Cookies to provide You with those services.</p>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <p><strong>Cookies Policy / Notice Acceptance Cookies</strong></p>\n" +
                "            <p>Type: Persistent Cookies</p>\n" +
                "            <p>Administered by: Us</p>\n" +
                "            <p>Purpose: These Cookies identify if users have accepted the use of cookies on the Website.</p>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <p><strong>Functionality Cookies</strong></p>\n" +
                "            <p>Type: Persistent Cookies</p>\n" +
                "            <p>Administered by: Us</p>\n" +
                "            <p>Purpose: These Cookies allow us to remember choices You make when You use the Website, such as remembering your login details or language preference. The purpose of these Cookies is to provide You with a more personal experience and to avoid You having to re-enter your preferences every time You use the Website.</p>\n" +
                "        </li>\n" +
                "                </ul>\n" +
                "    <p>For more information about the cookies we use and your choices regarding cookies, please visit our Cookies Policy.</p>\n" +
                "\n" +
                "<h2>Use of Your Personal Data</h2>\n" +
                "<p>The Company may use Personal Data for the following purposes:</p>\n" +
                "<ul>\n" +
                "    <li><strong>To provide and maintain our Service</strong>, including to monitor the usage of our Service.</li>\n" +
                "    <li><strong>To manage Your Account:</strong> to manage Your registration as a user of the Service. The Personal Data You provide can give You access to different functionalities of the Service that are available to You as a registered user.</li>\n" +
                "    <li><strong>For the performance of a contract:</strong> the development, compliance and undertaking of the purchase contract for the products, items or services You have purchased or of any other contract with Us through the Service.</li>\n" +
                "    <li><strong>To contact You:</strong> To contact You by email, telephone calls, SMS, or other equivalent forms of electronic communication, such as a mobile application's push notifications regarding updates or informative communications related to the functionalities, products or contracted services, including the security updates, when necessary or reasonable for their implementation.</li>\n" +
                "    <li><strong>To provide You</strong> with news, special offers and general information about other goods, services and events which we offer that are similar to those that you have already purchased or enquired about unless You have opted not to receive such information.</li>\n" +
                "    <li><strong>To manage Your requests:</strong> To attend and manage Your requests to Us.</li>\n" +
                "</ul>\n" +
                "\n" +
                "<p>We may share your personal information in the following situations:</p>\n" +
                "\n" +
                "<ul>\n" +
                "    <li><strong>With Service Providers:</strong> We may share Your personal information with Service Providers to monitor and analyze the use of our Service,  to contact You.</li>\n" +
                "    <li><strong>For Business transfers:</strong> We may share or transfer Your personal information in connection with, or during negotiations of, any merger, sale of Company assets, financing, or acquisition of all or a portion of our business to another company.</li>\n" +
                "    <li><strong>With Affiliates:</strong> We may share Your information with Our affiliates, in which case we will require those affiliates to honor this Privacy Policy. Affiliates include Our parent company and any other subsidiaries, joint venture partners or other companies that We control or that are under common control with Us.</li>\n" +
                "    <li><strong>With Business partners:</strong> We may share Your information with Our business partners to offer You certain products, services or promotions.</li>\n" +
                "    <li><strong>With other users:</strong> when You share personal information or otherwise interact in the public areas with other users, such information may be viewed by all users and may be publicly distributed outside. If You interact with other users or register through a Third-Party Social Media Service, Your contacts on the Third-Party Social Media Service may see You name, profile, pictures and description of Your activity. Similarly, other users will be able to view descriptions of Your activity, communicate with You and view Your profile.</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Retention of Your Personal Data</h2>\n" +
                "<p>The Company will retain Your Personal Data only for as long as is necessary for the purposes set out in this Privacy Policy. We will retain and use Your Personal Data to the extent necessary to comply with our legal obligations (for example, if we are required to retain your data to comply with applicable laws), resolve disputes, and enforce our legal agreements and policies.</p>\n" +
                "<p>The Company will also retain Usage Data for internal analysis purposes. Usage Data is generally retained for a shorter period of time, except when this data is used to strengthen the security or to improve the functionality of Our Service, or We are legally obligated to retain this data for longer time periods.</p>\n" +
                "\n" +
                "<h2>Transfer of Your Personal Data</h2>\n" +
                "<p>Your information, including Personal Data, is processed at the Company's operating offices and in any other places where the parties involved in the processing are located. It means that this information may be transferred to — and maintained on — computers located outside of Your state, province, country or other governmental jurisdiction where the data protection laws may differ than those from Your jurisdiction.</p>\n" +
                "<p>Your consent to this Privacy Policy followed by Your submission of such information represents Your agreement to that transfer.</p>\n" +
                "<p>The Company will take all steps reasonably necessary to ensure that Your data is treated securely and in accordance with this Privacy Policy and no transfer of Your Personal Data will take place to an organization or a country unless there are adequate controls in place including the security of Your data and other personal information.</p>\n" +
                "\n" +
                "<h2>Disclosure of Your Personal Data</h2>\n" +
                "<h3>Business Transactions</h3>\n" +
                "<p>If the Company is involved in a merger, acquisition or asset sale, Your Personal Data may be transferred. We will provide notice before Your Personal Data is transferred and becomes subject to a different Privacy Policy.</p>\n" +
                "<h3>Law enforcement</h3>\n" +
                "<p>Under certain circumstances, the Company may be required to disclose Your Personal Data if required to do so by law or in response to valid requests by public authorities (e.g. a court or a government agency).</p>\n" +
                "<h3>Other legal requirements</h3>\n" +
                "<p>The Company may disclose Your Personal Data in the good faith belief that such action is necessary to:</p>\n" +
                "<ul>\n" +
                "    <li>Comply with a legal obligation</li>\n" +
                "    <li>Protect and defend the rights or property of the Company</li>\n" +
                "    <li>Prevent or investigate possible wrongdoing in connection with the Service</li>\n" +
                "    <li>Protect the personal safety of Users of the Service or the public</li>\n" +
                "    <li>Protect against legal liability</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Security of Your Personal Data</h2>\n" +
                "<p>The security of Your Personal Data is important to Us, but remember that no method of transmission over the Internet, or method of electronic storage is 100% secure. While We strive to use commercially acceptable means to protect Your Personal Data, We cannot guarantee its absolute security.</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <h1>Children's Privacy</h1>\n" +
                "    <p>Our Service does not address anyone under the age of 13. We do not knowingly collect personally identifiable information from anyone under the age of 13. If You are a parent or guardian and You are aware that Your child has provided Us with Personal Data, please contact Us. If We become aware that We have collected Personal Data from anyone under the age of 13 without verification of parental consent, We take steps to remove that information from Our servers.</p>\n" +
                "    <p>If We need to rely on consent as a legal basis for processing Your information and Your country requires consent from a parent, We may require Your parent's consent before We collect and use that information.</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h1>Links to Other Websites</h1>\n" +
                "<p>Our Service may contain links to other websites that are not operated by Us. If You click on a third party link, You will be directed to that third party's site. We strongly advise You to review the Privacy Policy of every site You visit.</p>\n" +
                "<p>We have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.</p>\n" +
                "\n" +
                "<h1>Changes to this Privacy Policy</h1>\n" +
                "<p>We may update our Privacy Policy from time to time. We will notify You of any changes by posting the new Privacy Policy on this page.</p>\n" +
                "<p>We will let You know via email and/or a prominent notice on Our Service, prior to the change becoming effective and update the \"Last updated\" date at the top of this Privacy Policy.</p>\n" +
                "<p>You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.</p>\n" +
                "\n" +
                "<h1>Contact Us</h1>\n" +
                "<p>If you have any questions about this Privacy Policy, You can contact us:</p>\n" +
                "<ul>\n" +
                "          <li>By visiting this page on our website: https://sites.google.com/view/dbroker/home</li>\n" +
                "      </ul>",Html.FROM_HTML_MODE_COMPACT)
    }
}
