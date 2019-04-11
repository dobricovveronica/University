<%@ page import="java.util.Set" %>
<%@ page import="static java.util.Objects.isNull" %>
<%@ page import="java.util.Base64" %>
<%@ page import="com.amsoftgroup.model.*" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: vdobricov
  Date: 3/14/2019
  Time: 8:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add / Edit Student</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/views/css/style.css" %>
    </style>
    <script>
        function previewFile() {
            var preview = document.querySelector('img');
            var el = document.querySelector('input[type=file]');
            var file;
            var image = "/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgCWAJYAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A+2KKKKAE/ipaKKAE6UtIvSloAKKKKACiikIzQAtFFIvSgBaKT+KloAKKTbS0AFFFFABRTKdtoAWikXpS0AJzS0UUAIc0c0daWgBP1paKKACiiigAooooAKKKKACiiigAooooAKKKKACik/ipaAAd6KKKACiiigAooooAKMmiigAooooAKKKKACiiigBF6UtFFACN0paKTigBaKRulLQAUUUUAFFFFABRRRQAUUUUAFFFFACbqOlLSbaAAdTRSgYIooAKKKKACiiigBG6UbaWigBGGKNtLRQAm2gjNBGaWgApMcUtFABSE4oXpS0AJtpaQjNLQAgGKNtH8NLQAjdKNtNp9ABRSfw0tABSbaWigBP4aWkbpRjigBB1o706k20ALRRRQAUUUUAFFFFABRRRQAUnWj+GjbQAtFFFABRRRQAZ60UUUAFFFFACbaWiigAooooAKT1o6UtABRRRQAUi9KWigApNtLRQAjdKP4qCM0tABRSfjRtoAWk20tFABRRSbaAFoopP4aAFopNtLQAUjdKG6UtABSZ7UdKWgAHUUUg6rRQAtFFFABRSbqWgAooooAKKKKACiiigBNtLRRQAUUnSloAKKKKACjHNI3SloAKKKKACiik/hoAWiiigAooooAKKKKACiiigAooooAKKKKAE/ipaKKACiiigBOaWiigAooooAKKKKACiik20AHSloooAKKKT8KABulLRRQAUUUUAFJtpaKACkbpS0UAFFFFABRRSbaAFooooAKKTpR1oAWiiigAooooAMc0jdKB16UtABRRRQAL2ooooAKT+KlpNtAC0UUUAFFFFABRRRQAnSlo4/GigAooooAKKKKACiiigAooooAKKKKACiiigAHeiiigAooooAKTpS0UAFJtpaKACiiigAooooAKKKKACiiigAooooAKKKKAE3UtFFABRRRQAUUUUAFFFI3SgBaTdS0UAFJupaKACiiigApDwaWigAooooAKKKKACkbpTuPekoAKKKKACk/ClooATrS0UUAFFFFABRRRQADrRQOtFACfxUL0o5o3UALRSZ4NG6gBaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKT+GloAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooATdS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAA60UDrRQAHrSL0paKACiik60ALRRR/EKACiiigApOtH8NLQAUUUUAFFFFABRRRQAUUjdKWgApP4qWigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAB1ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAAdaKB1FFABRSN0paAE20tFIeooAOtLRRQAUUUUAFFFFABRRRQAm6loooAKKKKACiiigAooooAKKT+KjrQAtFFIvSgA3UtFFABRRRQAgOaWiigAooooAKKBk9AT9K07Pw1qV/jyrSTB/iYYFAGZRXVW3w51KUjzXihH13VpxfC8D/W3ueP4VxQBwWc0V6NH8MbMA77qUnP8IFO/wCFY2P/AD8z/pQB5tn1pa9Df4Y2275LuQD3AzVOf4YShSYr1Tjsy0AcRRXSXPw/1WHlUjlH+y3NYt3pd3Yn/SLaSIdMstAFWijv0ooAKKKKAE3UtFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFJ9aAFooooAKKKKACiiigAooooAB1FFA60UAJ0paKKAEHU0bqF6UtABRRSN0oAWik6UtABRRRQAUUUUAFJtpaKAE/hpaKKACiiigAooooAKT+KlooAKKKKACiiigAooooAKP51oaToN5rL4toiyjguegr0HQPAtnpiLJdKtzcep+6PoKAOA0zw5f6rIFht2C93YYArsNN+GsEfzXkxkP91OBXbBAqgAYA7AU7AoAz7HQ7HTgPIto0I/ixz+dX9tOooAbtp1FFABRRRQAUmBS0UAJx7Ux0DqVYAg8EHpUlFAGHqHg/TNRB326xsf4oxg1yuqfDeaHe9lKJVzkRtwcV6L2pNtAHht3p9zYsVnheMg4yw4qCvc7qyhvIjHPEsqHswrh9f+HfzmXTSAveFj/KgDhKKlu7SaxmaKeMxyDqpqKgAo/SiigAooooAKKKKACiiigAooooAKKKKAF496SiigAooooAKKKKACiiigAooooAKKQnFLQAUUUUAA6iigdaKACiiigBAMUfxUcY4o6GgBaKQHNLQAUUm6loAKKKT+GgBaKKKACiikbpQAtFFFABRRRQAUUUUAFFFFACbqOnalooAKRulLT4YHuZliiUvIxwFHegBiqXYBRknoBXZ+GPAb3YW51AGOPOVi7t9fStrwr4Mj0wLcXaLJc9geQtdYAMUARW1pDaRCOGNYkH8KipdtL2paACiiigAooooAKKKKACiiigApMilooAKKKKACiiigBCM0m2nUUAZesaBZ6zCyXEY3kYEgHzCvM/EHhW60KZjjzbb+GUf19K9fIzUU0MdxG0cih0bgqwyDQB4VRXXeLPBb6cWurNS1uOWQdVrkPrQAucdqKKKACiiigAooooAKTdS0UAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADqKKB1FFACfxU2n0UANzxigcml3UL0oAWiiigAooooAKKKKACiiigAooooAKKKKADpiiik3UALRRRQAUUUm6gBaRelLQoLEAAknsKAHQwPcypFEpeRzgKOpr1Xwl4Wi0W2WWVQ944yzEfd9hVTwV4VGmwJd3C/6U4yF/uiutXrQAuBS0UUAFFFFABRSdqWgBMClopD0oAWiiigBMCloooAKKKKACiikHSgAwKWiigAooooATAowKWigBjqsgKsAQeCDXnnjbwh9lJvrKP8AdH/WRqPu+4r0amOgdSpGQRg0AeD0V1HjTwsdIm+1W65tnPOP4TXL0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADrRQOoooAD1pF6UtJjigAHU0elC9KOlAC0UUh6igAbpS0UUAFFFFABRRRQAUUUUAI3SlopOaAFooooAKTdS0UAFFFJuoAWuw8CeFxfSi+uUPkp/q1I4Y+tc5ommPq+pQ2y9GPzH0XvXstjZx2FtHbxLtjjGBQBOBinUUUAFFFFABRSZFLQAUUUh6UAB6UtJkUtABRRRQAUUUUAFJ3paKACiiigBB0paKKAEPSloooAKKKKACiik6igCvfWUWoWrwToJInGCDXj+v6HLoeoPA2TH1jc/xCvaO1c94w0Bda01mVf9IiBZD6+1AHk1FIQVJGMEHpS0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUn4UAHBpaKKACk20tFABRRRQAUUmfaloAB1FFA60UAFJtpaTdQAtFMp38VAB1obpS0i9KAFopePem7aAFooooAT+KloooAKKKKACiiigAooooAKKTbR1oAP4qWir2haa+q6rBboPvNlj6AUAd/4C0FbCwF5ImLiYZGey111RxRCKNUHRRgVJQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFJ1FLRQB5X470FdL1AXEK7YZ8n6N3rmK9g8W6U2r6LNEv31+dfqK8fIKsVPBHGKACiiigAooooAKKKKACiiigAPWiiigAz7UUUUAFFFFABRRRQAUUUUAFFFFAAOtFA60UAFJ1paKAE6ij+KlooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACk20tFACfxV33wz03C3F6xOT+7Udvf+lcFgk4HOa9k8MWP9n6LaxEYbbuP1NAGvRRRQAUUUUAFFFFABSd6WigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKTvS0UAFFFFACHpS0UUAFFFFACHkV474t006XrlxGCSjHepPvXsWRXCfE2x3RWt2FztJjY/XkUAcBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFJupaACiiigAHUUUDqKKACiiigBlO/ioXpR/FQAtJuo60tABRRRQAUUUUAA4oooHOKACiiigAooooAKTpS0UAFFFFAF3RbQ3uq2sIP3nFe1ooRVA6AYryrwBbGfxDG3/ADyUv9O39a9YoAKKKQdKAAdKWiigAooooAKKKKACkyKWigAooooAKKTvS0AFFFFABRRRQAUUUUAJ0FLRRQAmRS0UUAFFJ3paACiiigAoopO9AB2rF8X2X23QLpB95V3Dj0rbqC7j860mj/vIR+lAHhQ6UtPuI/JnkQ/wsVplABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAAOoooHUUUAFJ/DS0UAIvSj+KhelLQAUUUUAFJ/DS0UAFFJtpaACiiigBG6UL0paKACiiigAooooAKKKKAO0+GMO69u5eOEC+/J/+tXo9cD8L4wU1B/4gUH6Gu+oAKKKKACiiigAooooAKKKKACiiigBB0paKKACiiigAooooAKKKKACiiigAooooAKKKKACiikyKAFpO1HPtS0AFFFFABSHpS01utAHiuvxeRrN4gxxIelUK1/F6CPxHfAdN+f0rIoAKKTdS0AFFJzRk+tAC0UUUAFFFFABRSfw0tACfxUtFFABRRRQAUUUmeaAFooooAB1FFA60UAFI3SlooAKTbRuoXpQAtFFJ0oAP4aWiigAooooAKKKKACiiigAoooPFABRRRQAUUUUAd/8Lv8AUah/vJ/Jq7yvPvhi5D3yZ4O0ke4zXoNABSZFLRQAUUUUAIelGRS0UAFFFFABRRRQAUUmRS0AFFFFABRRRQAUUUUAFFFFABRRRQAUmeM0tFACZFLRRQAUUUnUUALRRRQAUUU09BQB4/4y/wCRlvv9/wDpWL+FavilzJ4gvSTk7+tZdABSbaATS0AJtpaKRulAC0UUUAFJtpV96KACk20bqWgBOtLSL0paAA9qKKKACiik/ioAWiiigAHWigdRRQAUUUEUAFFJ/FQDmgBaKKKAEXpS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAdb8NrgR6xLGf44jj6givTq8d8HXAtvEVoxIAJ2n8a9gJzQA6iiigAooooAKKKKACiiigAooooAKKKKACik/LFLQAUUnajvQAtFFFABSYFLSYFAC0UUUAFFJ1FLQAUUUUAFFFFABRRRQAUx22KSeg5px6VS1icW2l3Mh/hQ0AeN6pL5+pXL9MyN/Oq1BJYknqeaKACiigHFABSN0paTbQAtJ1paKACiiigBF6UtFFABRRRQAUUUUAFJ/FS0UAFFFFAAOoooHWigAooooATrS0UUAFFFFABRSN0obpQAtFFIvSgBaKKKACiiigAooooAKKKKACiiigCS2mNvcRyjqjBuK9usrkXdpDMp3K6g5rw2vU/AGofbdEWMkl4TsOfSgDqKKKKACiiigAooooAKKTvS0AIelLRRQAUUneloAKKKKACiiigAoopO9AC0UnaloAKKTvS0AFJ2paKACiiigBOopaKTIoAWiik6CgBD0Fcv8AEK9FroZiz80zBce1dQ3WvNPiPqP2jU4rYE7YVyQfU/8A6qAORooooAKKKKACkbpS0jdKAFooooAKKTbRg96AFooooAKKTpS0AFFJzS0AFJupaKACiiigAHaigdaKACik60tABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXUfD/VRYawYXYiOcbRzxu7f1rl6dFK0MqOpwVIINAHu+6l6Cs/RdRTVNOguEOdy8+x71o0AFFFFABRRRQAUnrS0mBQAtMp9FACdTS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAmRR2oPSloAKKTApaAEPSlopD0oAbT6KKAK93cpZW0k8hARFJJPFeKajdm/vp7hiSXYnk9q9A+Iuqrb6elmrfvJTkgHoBXm5xmgApOlLRQAnWloooAKQnmhulKBQAnfpS0UUAJ0o3UtFABSfw0DqaWgAooooAKTHNLRQAnSjdS0UAA70m6looAB1HFFA6iigBF6UtFFABSfxUtFACdaWiigBOlLRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAmeKUDOaKKAOt8Aa+1nfCxlf8AcS/dz2avTAea8HR2idXQlXU5BHavXPCWvLrenIWbNzGNsg9/WgDeooooAKKKKACiiigAooooAKKTqKWgApB0paKAEPSlpMiloAZT6KKACiiigAooooAKKKKACiiigApD0paKACq93cpZW0k0jBUQbjVivPfiD4i3sNOgk4H+tx/KgDk9Z1aXWdQkuZT1OFA7DsKpUUhGaAFopP4aWgAooooAKKKB1oAKKKKACk60tJzQAtFFFABSbqWigBPwo3UtFABSfxUtFABRRRQAg6milXtRQAUUUUAJjmloooAKKRelLQAjdKWiigAooooAKKKKACiiigAooooAKKKT+KgBaKKKACiiigArQ0PWZtEvknjYhc4dexFZ9J1oA9x06/h1K0juIG3RyAEe3tVuvJPCXid9DuBHKSbRz8wH8PvXqlvPHcwrLE4eNxlWB4IoAnooooATIpaKKACk/ClooAKKKTIoAOgpab0NOoAKKTtS0AFJ3paKACiiigApPwpaQdKAFooooAKKKKACikyKyte1yDQ7NppWBc5CIDyxoAp+LvEa6HYlY2BupBhB6e9eUSSNLIzuxZ2OST1NWNT1KbVbpp523Of0FVaACik3UufegAopAc0fxUALSdKWkXpQAtFFJuoAWik/io/ioAWiiigAopM5FLQAUUnNLQAUUUm6gBaKTdS0AFFFFAAOtFC/1ooAKKKMYoAKKKKACiiigAooooAKKKKACl496SigAooooAKKKKACiiigAooooAKKKKACiiigBNtdN4U8YS6Ky20/7y0J7nlPpXNUUAe6W11FdRLJE6ujcgg1NkV5B4c8VXOgyhf9bbH70Z/pXp+k6za6xbCW3kDDup6j60AaNFJkUtABRRRQAUUUUAFFFFABRRRQAmBS0UUAJ0FLRRQAUUUUAFFFN3UAOopMiuc8SeMLbRFaNCJrojhAeB9aAL2va/b6HbNJKwMn8MYPJNeU63rdxrl2Z5246Kg6KKh1HU59UuTPcvvc/kKrUAFFFFABSfw0tJ70ALRSfxUtABRRRQAUUnWloAKKKT+dAC0UUUAFFI3SloATqKWk20tACfw0tFFABRSdaNtAC0UUUAA60UDg0UAFFJ1paAE6UtJtpaACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKtadqdzpVws1tIUYEEjsfrVRulW7LSrvUmAtrd5cnGQOKAPQvD/j22vwI7zbbT9Ac/K3+FdWkiyKGVgynoRzmvP9K+G0rlHvpgi4yY4+T+ddtpelxaRaiCEsUHOWOTQBeoopMCgBaKKKACiiigApMilooAQdKMilooATApaKKACkwKWk6igAJxVa9v7ewiMlxKsSDuxqyRmue8Q+D4deYyNNJHLj5ecqPwoA5vxD8QXn32+njbGRgzHqfp6VxckjSuXdi7sclj1Nb+q+B9S035kj+0x+sfUfhWA8bRMVdSrDggigBKKRelLQAUUi9KWgAooooAKKKKACik20bqAFoopOpoAWiiigAopNtLQAn8NLRRQAUn8NLRQAUUUUAFFFIBigBaKKKAAHkUUDqKKACiiigAooooATpS0UUAFFFFABRRRQAUUUUAI3SloooAKKKKACiiigAooooAKKKKACiipLa2lu5VjhjaRzxhRmgCOrmm6LeatKEtoWcd2xwPxrstB+HgRlm1Fg/fyV6fjXbW9tFaxiOGNY4x0VRgUAchovw7gt9sl+/nP18tfuiuwt7WK1iEcKLGg6KoxUuOMUYFACbadRRQAh6UdRR0FGBQAtFFFABRRRQAUUUUAFFFFABSdRS0UAJ1FLRRQAUhGaWigBvFZWreG7HWFPnwgP2kUYatekwKAPLta8A3lhue1/0mH0H3hXLvG0TlXUqw6gjmveNtZOteGrLW4mEsYWUj5ZVHzCgDxtulDdK39e8H3mi/OB58GfvoOn1rAbpQAtJ/FS0UAIvSlopG6UALRRRQAUnQ0tFABRRRQAUUUUAFFFFABRRSbqADdS0UUAI3SjdS0UAFIvSloJoABjNFA6iigAooooAKTbS0UAIvSjdS0UAJ0pTxRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUKrOwVQSScYFdt4V8CNPtutQG1DysPc/WgDE0Dwnd65IGA8q37yt/SvTNH0Cz0SLZbR4J6u3JNX4okgQJGoRRxgCpOgoAMCgDFLSZFAC0h6UtFABSHpS0UAFFFJ2oAWiikHSgA6iloooATj2paKTIoAWkyKWkyKAFoopOooAWiiigAooooAQ9KWkyKWgApoGadSE4oAayBlIIBHoa47xF4ChvN09h+6m5Zo/wCFq7Sm/hQB4VdWk1jM0U8ZjkHBU1HXsmueHbXXINkqhZB92QDkV5brfh+60K4Mcy7oz92RehoAzaKRulBGaAFooooAKQDJpaKACiiigAooooAKKKKAE20N0o60tABRRRQAUUUUAFFFFACDqaKWigAooooAKKRelLQAUUUUAFFI3SloAKKKKACiiigAooooAKKKKACiiigAp8EElzKscal3Y4AFFvbyXUyRRIXkc4AFeo+FvCEWixrNMA92Rz6L9KAKvhPwWmnKt1eKHuTyE6hP/r12GBR2paAEwKWikAxQAtFFFABRRRQAUUUmBQAtFFFABSdRS0nUUALRRRQAUUUUAFFJ0FLQAUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnQUtACYFVdQ0+DUrdoZ0Dowx9Kt0UAeR+JvCc+hSl0BktD0fuPY1z/8AFXut3bR3kDRSqHRhgg15h4s8IvormeAGS0bvjlD70AcyvSloooAKKKKACiiigAooooAKKTrS0AFI3SlooAKRulLRQAUUUUAFFFFAAOoooHUUUAFFFFABRRRQAUUUUAFFJ1paACiiigAooooAKKKKACiiigAzinxRPcSpFGMuxwB60wAsQByT0Fek+CfCa2EK3tyga6b7oP8AAP8AGgC14P8ACq6NB58yhrlx1x90e1dOBxQBxS0AFFFFABRRRQAUUUUAIelLRRQAUUUUAJ1FLRRQAmBS0UUAJgUtIelLQAUUUUAFJ3paKACik7UtABRRRQAUnUUtFACHpS0UnQUALSYFLRQAUUUUAFFFFABUU0KTxNHIodGGCpGQalooA8l8WeFX0OYyxjNqx4P92uer3O8tIr6B4ZkDxsMEGvJPE/h6TQL5lAJt2OY29vQ0AY9Jto3UtACbaWiigBF6UAYpaKACiiigAooooAKKKTdQAtFFFABRRRQADqKKB1ooAKT+KlooAKKKM5oAKKKKAE/hpQaT+KloAKKKKACiiigAooooAKKK2vCvh9tevwD8tvGcu39KANvwJ4XFy32+6TMY/wBUjD7x/vV6IuAMVHBCsESxxgKijAAHAqUdKAFooooAKKKKACiiigAooooAKKKKACk6ClooAKKKKACiiigAoopD0oAWikwKWgBMijIpaKACiikwKAFooooAKKTvS0AFFFFABRRRQAUUUUAFFFFABRRRQAh6VQ1fSYdYs3t5lBBHDd1PrWhSYFAHiGq6ZNpF9JbSjBU8E8bh61Ur1fxh4cXXLHfGALmIZU+vsa8okRo3ZWGGBwR6UAFFFJ/FQAtFFFABRSbaG6UALRRRQAHmiik20ALRRRQAUUUUAA60UDqKKACiiigApOlG6loAKQHNLRQAnvS0UUAFFFJuoAWiiigAooooAltbWS9uEhiUs7nAwK9j0HRo9E02O2Q5IGWb1Nc18PdAWO3/ALRlU+a/EYPYetduBigAAxS0UUAFIOlLRQAUUUUAFFFFABRRRQAUh6UtFABRRRQAUUUUAFJgUtMoAfRRRQAUUynYFAC0U3bTqACiiigAooooAKKKTtQAtFFFABRRRQAUUUUAFFN/GnUAFFFFABRRTKAF21558QfDvkS/2jApKucSADofWvRMCoLq1S8t5IZRuRxtYe1AHhlFaGv6S2i6nLbHOwHKE9xWfQAUUUUABGKKKKAE3UtFJ/DQAEZo/hpaKACiik/ioAWik/hpaAAHmikHVaKAFooooAT8KN1LRQAUUUUAFFFFABRRRQAUUUUAFanhvSG1nVYYcHywdznHassAngda9X8E6GNJ0tJHQC4mG5jjkDsKAN+KJYY1RFCKowABxUtFFABRRRQAUg6UtFABRRRQAnQUtFFACYFLRRQAUUUUAFFFFABRRRQAUUUUAFFFJ3oAWiiigAopOgpaACkPSlooAKKKKACik70tABRSfhS0AFFFFABSDpS0UAJgUtFFABRRRQAUUUUAFNAzS4FLQBy/jnRBqelNNGoM8PzDA5I7ivLK95KhgQQCD1Brybxron9kaszRrtgm+ZQBwPUUAc/SZ5paRelAC0UUjdKADpS0nSloAKKKKAEBzS0UUAJ/DS0nNHSgBR1ooHUUUAFFB60h6tQAtJ/FS0UAFFFFABRRRQAUUUUAFFFJ1oA3PB+jnV9YjDDMMXzuf6V68oC4A4A7VzPgLSxYaMsjIVkn+Y59O1dRQAUh6UtFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUh6UtFABRRRQAUUUUAFFFFABRSE4oyKAFooooAKKKKACiiigBOopaKKACik/GloAToKWiigAoopO9AC0gGKWigAopMiloAKKKTIoAWkPSlooAKwPGGjDWNIkCrmaP50reyKT+GgDwUjaSCORwaWt3xppY03XJdiFYpfnX+tYVACbqWk9aM80ALRRRQAUUnWloAT0oPUUtFABSD0o/hpaAAdaKB1FFACfjRtpaKAE20baF6Ue1AC0UUUAFFFFABRRRQAVoeHtP/tPVreDaWUtlsegrPr0D4a6WUinvnA+f5EyPzoA7eOMRKqKMKowBUlFFABRSdqWgAooooAKKKKACiiigAooooAKKKKACiiigAooooAKQ9KMiloAKKKKAE6CloooATPtS0UmRQAZFLRRQAUUyn0AFJ0FLRQAnej8KWigBD0paKKACiiigApMilooAQdKWiigAooooAKTIpaToKAFoopOooAWkPSlooA5Tx/pYvdI89VJlgO4EenevLyTXutxEJ4XjYAhhg5rxbWLB9M1Ge3cYKsccdu1AFOik7GloAKKKKACiiigAopCM0tABRRRQAg6rRQOpooAWkXpRkUtABRRSL0oAWkXpS0UAFFFFABRRRQA6NGldUUZZjgCvaNDsF0zTLeBV2lVGQPWvMPBunf2hrsCkApH85B9q9fAwOKADtS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUnelpMCgA6ilopOgoAWiikwKAFooooAKKKTAoAMCloooAKKKKACiiigAooooAKKKKACiiigAooooATpmloooAKKKQDFAC0UUUAFIelLTKAH0UUUAJ2rzn4laWIbmC9RcLINjtnuOlej1heMdN/tHQp1ABdBvUn1FAHkC9KP4aWigApP4aWigApNtLSbqAFopN1LQAUUgOaWgBB1WigdVooAP4aN1LRQAi9KWiigAoooHNABRSbaWgAooooA9A+Gdjtgubs/xsEX8Otd3WL4TsjY6BaRn7xXecepraoAKKKKACiiigAopD0paACiikHSgA7UtJ2paACiiigAooooAKKKKACkwKWigApD0paKAEwKWiigAooooAKTApaKACiiigAooooATvTafRQAneloooAKKKKACiikPSgBtPoooAKKKKAEPSlpOopaACiiigApO1LSDpQAtJgUHpS0AFRyoJI2Q9GGKkprdaAPEdXtDYancwH+ByB9KqV1PxEsvs+sLNjCzL29a5agBP4qP4qWk60ALRRScUALRSbqWgBAcUfxUtJ1oAUdaKQYzRQAtFFFABRRSA+tAC0m2looATrS0UUAFWNOgN3f28IGd7har1veCLX7T4hgP8KZc5oA9YijWKNUAwFAAFS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUh6UALRRRQAUUUUAFFFFABRRSYFAC0UUUAFFJkUtABRRRQAUUUUAFFFFABRRRQAUh6UDpS0AFFFJ2oAWik6iloAKKKKAE6iloplAD6QdKWigApvU0uRTaAOO+JVmJNMguAOY3wT7GvOMcV7B4vtPtfh68QfeVN4+orx+gAoopN1AC0UUUAFFFFACL0paKKAAdaKAeRRQAUUhOKWgBN1H9KWigAooooAKKKKAEbpXb/AAytA1zdXBydqhRXE16T8NbcJpM0u3Bd+tAHZUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnaloAKKKKACiiigAooooAKKKQ9KAFooooAKKKTqKAFooooAKKKKAEPSlpB0paAEPSloooAKKKKACiim/w0AOooooAKKQ9KWgAooooAKKKKACiiigApO3rS0UAFFJ2paAEyKWiigApB0paKAIbmITW8kZ6MpBrw25h+zzyRHqjFefavdjyK8Y8SwC2128QLtXzCQPrQBm0UUUAI3SlopAc0ADdKWikzzQAtJ1o20tAAO1FJnHPpRQAtFFFABSfxUbaWgAooooATdS0UUAFes+BYBB4ct/ViWJryUnFezeG4jFoNiOuYlPT1FAGtRRRQAUUUUAFFFFABRRRQAUUUmRQAdBS0UUAFFJ3paACiiigAopD0paACiiigAooooATvS0UUAIelLSE4paACiik70ALRRRQAUUnajIoAMCloooAKKKTtQAtFFFABRRRQAnalpO9HagBaKKKACiiigBPwpaKKACiiigBMiloooAKKKToKAEbrXk/j2AReIpSOA6g16wegrzT4lx7NWt2zndH+XNAHI0UUUAFJ/FR/DS0AJ1o4paKACiiigBD3opaKAG5Wl3UUUAAOaBz6UUUAA6mloooAKQketFFACoRvHPevcNNUQ6fbIWGVjUfpRRQBZ3j1FG4eo/OiigA3j1FG8eooooAN49RRvHqKKKAFyPUUm8eooooAXI9RSbx6iiigA3D1H50uR6iiigBNw9R+dG4eo/OiigA3j1FG8eooooAXI9RRkeooooAMj1FJvHqKKKADePUUbgO9FFABuHqPzo3D1H50UUAG4Z6j86N49RRRQAbx6ijcPUfnRRQAbx6ijePUUUUAG8eoo3D1H50UUAG8eooDD1FFFABvHqKNw9R+dFFABuB7/rRvHqKKKADePUUbx6iiigA3D1H50bx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADcM9RRvHqKKKAAsMdR+def/E5Bvs3yDwRiiigDhdw9aWiigBMijiiigA3UtFFACbqOKKKAA8DtRRRQB//Z";

            console.log("file", file);
            var reader = new FileReader();
            if (el.value != '') {
                file = el.files[0];
            }
            reader.addEventListener("load", function () {
                preview.src = reader.result;
            }, false);

            if (file) {
                reader.readAsDataURL(file);
            }
        }

        function duplicate(onePhone) {
            var c = $(onePhone).parent(this).parent(this).clone().appendTo(".allphones");
            c.find('input[type="text"]').val('');
            c.find('input[type="hidden"]').val('');
        }

        function deleteBlock(onePhone) {
            var c = $(onePhone).parent(this).parent(this).remove();
        }
    </script>
</head>
<body>
<h1>Add / Edit Student</h1>
<form method="post" enctype="multipart/form-data" id="editsavestudentform">
    <% Student student = new Student();
        long studentId = (Long) request.getAttribute("studentId");
        if (!isNull(studentId) && studentId != 0) {
            student = (Student) request.getAttribute("student");
        }
        Set<PhoneType> phoneTypes = (Set<PhoneType>) request.getAttribute("phoneTypes");
    %>
    <div class="form-group row">
        <div class="col-1">
            <input type="hidden" name="action" value="EDIT">
            <input type="hidden" name="studentId" <%if (studentId!=0){%>value="<%=student.getId()%>"<%}else{%>
                   value=""<%}%>>
            <label class="col-sm-2 col-form-label">FirstName:</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" placeholder="First name" name="first_name"
                   value="<%=(studentId!=0) ? student.getFirstName():""%>" required>
        </div>

    </div>
    <div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">LastName:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Last name" name="last_name"
                       value="<%=(studentId!=0)?student.getLastName():""%>" required>
            </div>

        </div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-3 col-form-label">Date:</label>
            </div>
            <div class="col-3">
                <input type="date" class="form-control" name="date_of_birth" placeholder="Date of birth"
                       value="<%=(studentId!=0)?student.getDateOfBirth():""%>" required>
            </div>
        </div>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Gender:</label>
            </div>
            <div class="col-2">
                <div class="form-check form-check-inline">
                    <input class="form-check-input col-4" type="radio" name="gender"
                           value="M" <%=(student.getGender()=='M')?"checked":"checked"%>>Male<br>
                    <input class="form-check-input col-4" type="radio" name="gender"
                           value="F" <%=(student.getGender()=='F')?"checked":""%>>Female<br>
                </div>
            </div>
            <div class="col-2"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Picture:</label>
            </div>
            <div class="col-3">
                <%
                    String image = "/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgCWAJYAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A+2KKKKAE/ipaKKAE6UtIvSloAKKKKACiikIzQAtFFIvSgBaKT+KloAKKTbS0AFFFFABRTKdtoAWikXpS0AJzS0UUAIc0c0daWgBP1paKKACiiigAooooAKKKKACiiigAooooAKKKKACik/ipaAAd6KKKACiiigAooooAKMmiigAooooAKKKKACiiigBF6UtFFACN0paKTigBaKRulLQAUUUUAFFFFABRRRQAUUUUAFFFFACbqOlLSbaAAdTRSgYIooAKKKKACiiigBG6UbaWigBGGKNtLRQAm2gjNBGaWgApMcUtFABSE4oXpS0AJtpaQjNLQAgGKNtH8NLQAjdKNtNp9ABRSfw0tABSbaWigBP4aWkbpRjigBB1o706k20ALRRRQAUUUUAFFFFABRRRQAUnWj+GjbQAtFFFABRRRQAZ60UUUAFFFFACbaWiigAooooAKT1o6UtABRRRQAUi9KWigApNtLRQAjdKP4qCM0tABRSfjRtoAWk20tFABRRSbaAFoopP4aAFopNtLQAUjdKG6UtABSZ7UdKWgAHUUUg6rRQAtFFFABRSbqWgAooooAKKKKACiiigBNtLRRQAUUnSloAKKKKACjHNI3SloAKKKKACiik/hoAWiiigAooooAKKKKACiiigAooooAKKKKAE/ipaKKACiiigBOaWiigAooooAKKKKACiik20AHSloooAKKKT8KABulLRRQAUUUUAFJtpaKACkbpS0UAFFFFABRRSbaAFooooAKKTpR1oAWiiigAooooAMc0jdKB16UtABRRRQAL2ooooAKT+KlpNtAC0UUUAFFFFABRRRQAnSlo4/GigAooooAKKKKACiiigAooooAKKKKACiiigAHeiiigAooooAKTpS0UAFJtpaKACiiigAooooAKKKKACiiigAooooAKKKKAE3UtFFABRRRQAUUUUAFFFI3SgBaTdS0UAFJupaKACiiigApDwaWigAooooAKKKKACkbpTuPekoAKKKKACk/ClooATrS0UUAFFFFABRRRQADrRQOtFACfxUL0o5o3UALRSZ4NG6gBaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKT+GloAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooATdS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAA60UDrRQAHrSL0paKACiik60ALRRR/EKACiiigApOtH8NLQAUUUUAFFFFABRRRQAUUjdKWgApP4qWigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAB1ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAAdaKB1FFABRSN0paAE20tFIeooAOtLRRQAUUUUAFFFFABRRRQAm6loooAKKKKACiiigAooooAKKT+KjrQAtFFIvSgA3UtFFABRRRQAgOaWiigAooooAKKBk9AT9K07Pw1qV/jyrSTB/iYYFAGZRXVW3w51KUjzXihH13VpxfC8D/W3ueP4VxQBwWc0V6NH8MbMA77qUnP8IFO/wCFY2P/AD8z/pQB5tn1pa9Df4Y2275LuQD3AzVOf4YShSYr1Tjsy0AcRRXSXPw/1WHlUjlH+y3NYt3pd3Yn/SLaSIdMstAFWijv0ooAKKKKAE3UtFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFJ9aAFooooAKKKKACiiigAooooAB1FFA60UAJ0paKKAEHU0bqF6UtABRRSN0oAWik6UtABRRRQAUUUUAFJtpaKAE/hpaKKACiiigAooooAKT+KlooAKKKKACiiigAooooAKP51oaToN5rL4toiyjguegr0HQPAtnpiLJdKtzcep+6PoKAOA0zw5f6rIFht2C93YYArsNN+GsEfzXkxkP91OBXbBAqgAYA7AU7AoAz7HQ7HTgPIto0I/ixz+dX9tOooAbtp1FFABRRRQAUmBS0UAJx7Ux0DqVYAg8EHpUlFAGHqHg/TNRB326xsf4oxg1yuqfDeaHe9lKJVzkRtwcV6L2pNtAHht3p9zYsVnheMg4yw4qCvc7qyhvIjHPEsqHswrh9f+HfzmXTSAveFj/KgDhKKlu7SaxmaKeMxyDqpqKgAo/SiigAooooAKKKKACiiigAooooAKKKKAF496SiigAooooAKKKKACiiigAooooAKKQnFLQAUUUUAA6iigdaKACiiigBAMUfxUcY4o6GgBaKQHNLQAUUm6loAKKKT+GgBaKKKACiikbpQAtFFFABRRRQAUUUUAFFFFACbqOnalooAKRulLT4YHuZliiUvIxwFHegBiqXYBRknoBXZ+GPAb3YW51AGOPOVi7t9fStrwr4Mj0wLcXaLJc9geQtdYAMUARW1pDaRCOGNYkH8KipdtL2paACiiigAooooAKKKKACiiigApMilooAKKKKACiiigBCM0m2nUUAZesaBZ6zCyXEY3kYEgHzCvM/EHhW60KZjjzbb+GUf19K9fIzUU0MdxG0cih0bgqwyDQB4VRXXeLPBb6cWurNS1uOWQdVrkPrQAucdqKKKACiiigAooooAKTdS0UAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADqKKB1FFACfxU2n0UANzxigcml3UL0oAWiiigAooooAKKKKACiiigAooooAKKKKADpiiik3UALRRRQAUUUm6gBaRelLQoLEAAknsKAHQwPcypFEpeRzgKOpr1Xwl4Wi0W2WWVQ944yzEfd9hVTwV4VGmwJd3C/6U4yF/uiutXrQAuBS0UUAFFFFABRSdqWgBMClopD0oAWiiigBMCloooAKKKKACiikHSgAwKWiigAooooATAowKWigBjqsgKsAQeCDXnnjbwh9lJvrKP8AdH/WRqPu+4r0amOgdSpGQRg0AeD0V1HjTwsdIm+1W65tnPOP4TXL0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADrRQOoooAD1pF6UtJjigAHU0elC9KOlAC0UUh6igAbpS0UUAFFFFABRRRQAUUUUAI3SlopOaAFooooAKTdS0UAFFFJuoAWuw8CeFxfSi+uUPkp/q1I4Y+tc5ommPq+pQ2y9GPzH0XvXstjZx2FtHbxLtjjGBQBOBinUUUAFFFFABRSZFLQAUUUh6UAB6UtJkUtABRRRQAUUUUAFJ3paKACiiigBB0paKKAEPSloooAKKKKACiik6igCvfWUWoWrwToJInGCDXj+v6HLoeoPA2TH1jc/xCvaO1c94w0Bda01mVf9IiBZD6+1AHk1FIQVJGMEHpS0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUn4UAHBpaKKACk20tFABRRRQAUUmfaloAB1FFA60UAFJtpaTdQAtFMp38VAB1obpS0i9KAFopePem7aAFooooAT+KloooAKKKKACiiigAooooAKKTbR1oAP4qWir2haa+q6rBboPvNlj6AUAd/4C0FbCwF5ImLiYZGey111RxRCKNUHRRgVJQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFJ1FLRQB5X470FdL1AXEK7YZ8n6N3rmK9g8W6U2r6LNEv31+dfqK8fIKsVPBHGKACiiigAooooAKKKKACiiigAPWiiigAz7UUUUAFFFFABRRRQAUUUUAFFFFAAOtFA60UAFJ1paKAE6ij+KlooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACk20tFACfxV33wz03C3F6xOT+7Udvf+lcFgk4HOa9k8MWP9n6LaxEYbbuP1NAGvRRRQAUUUUAFFFFABSd6WigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKTvS0UAFFFFACHpS0UUAFFFFACHkV474t006XrlxGCSjHepPvXsWRXCfE2x3RWt2FztJjY/XkUAcBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFJupaACiiigAHUUUDqKKACiiigBlO/ioXpR/FQAtJuo60tABRRRQAUUUUAA4oooHOKACiiigAooooAKTpS0UAFFFFAF3RbQ3uq2sIP3nFe1ooRVA6AYryrwBbGfxDG3/ADyUv9O39a9YoAKKKQdKAAdKWiigAooooAKKKKACkyKWigAooooAKKTvS0AFFFFABRRRQAUUUUAJ0FLRRQAmRS0UUAFFJ3paACiiigAoopO9AB2rF8X2X23QLpB95V3Dj0rbqC7j860mj/vIR+lAHhQ6UtPuI/JnkQ/wsVplABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAAOoooHUUUAFJ/DS0UAIvSj+KhelLQAUUUUAFJ/DS0UAFFJtpaACiiigBG6UL0paKACiiigAooooAKKKKAO0+GMO69u5eOEC+/J/+tXo9cD8L4wU1B/4gUH6Gu+oAKKKKACiiigAooooAKKKKACiiigBB0paKKACiiigAooooAKKKKACiiigAooooAKKKKACiikyKAFpO1HPtS0AFFFFABSHpS01utAHiuvxeRrN4gxxIelUK1/F6CPxHfAdN+f0rIoAKKTdS0AFFJzRk+tAC0UUUAFFFFABRSfw0tACfxUtFFABRRRQAUUUmeaAFooooAB1FFA60UAFI3SlooAKTbRuoXpQAtFFJ0oAP4aWiigAooooAKKKKACiiigAoooPFABRRRQAUUUUAd/8Lv8AUah/vJ/Jq7yvPvhi5D3yZ4O0ke4zXoNABSZFLRQAUUUUAIelGRS0UAFFFFABRRRQAUUmRS0AFFFFABRRRQAUUUUAFFFFABRRRQAUmeM0tFACZFLRRQAUUUnUUALRRRQAUUU09BQB4/4y/wCRlvv9/wDpWL+FavilzJ4gvSTk7+tZdABSbaATS0AJtpaKRulAC0UUUAFJtpV96KACk20bqWgBOtLSL0paAA9qKKKACiik/ioAWiiigAHWigdRRQAUUUEUAFFJ/FQDmgBaKKKAEXpS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAdb8NrgR6xLGf44jj6givTq8d8HXAtvEVoxIAJ2n8a9gJzQA6iiigAooooAKKKKACiiigAooooAKKKKACik/LFLQAUUnajvQAtFFFABSYFLSYFAC0UUUAFFJ1FLQAUUUUAFFFFABRRRQAUx22KSeg5px6VS1icW2l3Mh/hQ0AeN6pL5+pXL9MyN/Oq1BJYknqeaKACiigHFABSN0paTbQAtJ1paKACiiigBF6UtFFABRRRQAUUUUAFJ/FS0UAFFFFAAOoooHWigAooooATrS0UUAFFFFABRSN0obpQAtFFIvSgBaKKKACiiigAooooAKKKKACiiigCS2mNvcRyjqjBuK9usrkXdpDMp3K6g5rw2vU/AGofbdEWMkl4TsOfSgDqKKKKACiiigAooooAKKTvS0AIelLRRQAUUneloAKKKKACiiigAoopO9AC0UnaloAKKTvS0AFJ2paKACiiigBOopaKTIoAWiik6CgBD0Fcv8AEK9FroZiz80zBce1dQ3WvNPiPqP2jU4rYE7YVyQfU/8A6qAORooooAKKKKACkbpS0jdKAFooooAKKTbRg96AFooooAKKTpS0AFFJzS0AFJupaKACiiigAHaigdaKACik60tABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXUfD/VRYawYXYiOcbRzxu7f1rl6dFK0MqOpwVIINAHu+6l6Cs/RdRTVNOguEOdy8+x71o0AFFFFABRRRQAUnrS0mBQAtMp9FACdTS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAmRR2oPSloAKKTApaAEPSlopD0oAbT6KKAK93cpZW0k8hARFJJPFeKajdm/vp7hiSXYnk9q9A+Iuqrb6elmrfvJTkgHoBXm5xmgApOlLRQAnWloooAKQnmhulKBQAnfpS0UUAJ0o3UtFABSfw0DqaWgAooooAKTHNLRQAnSjdS0UAA70m6looAB1HFFA6iigBF6UtFFABSfxUtFACdaWiigBOlLRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAmeKUDOaKKAOt8Aa+1nfCxlf8AcS/dz2avTAea8HR2idXQlXU5BHavXPCWvLrenIWbNzGNsg9/WgDeooooAKKKKACiiigAooooAKKTqKWgApB0paKAEPSlpMiloAZT6KKACiiigAooooAKKKKACiiigApD0paKACq93cpZW0k0jBUQbjVivPfiD4i3sNOgk4H+tx/KgDk9Z1aXWdQkuZT1OFA7DsKpUUhGaAFopP4aWgAooooAKKKB1oAKKKKACk60tJzQAtFFFABSbqWigBPwo3UtFABSfxUtFABRRRQAg6milXtRQAUUUUAJjmloooAKKRelLQAjdKWiigAooooAKKKKACiiigAooooAKKKT+KgBaKKKACiiigArQ0PWZtEvknjYhc4dexFZ9J1oA9x06/h1K0juIG3RyAEe3tVuvJPCXid9DuBHKSbRz8wH8PvXqlvPHcwrLE4eNxlWB4IoAnooooATIpaKKACk/ClooAKKKTIoAOgpab0NOoAKKTtS0AFJ3paKACiiigApPwpaQdKAFooooAKKKKACikyKyte1yDQ7NppWBc5CIDyxoAp+LvEa6HYlY2BupBhB6e9eUSSNLIzuxZ2OST1NWNT1KbVbpp523Of0FVaACik3UufegAopAc0fxUALSdKWkXpQAtFFJuoAWik/io/ioAWiiigAopM5FLQAUUnNLQAUUUm6gBaKTdS0AFFFFAAOtFC/1ooAKKKMYoAKKKKACiiigAooooAKKKKACl496SigAooooAKKKKACiiigAooooAKKKKACiiigBNtdN4U8YS6Ky20/7y0J7nlPpXNUUAe6W11FdRLJE6ujcgg1NkV5B4c8VXOgyhf9bbH70Z/pXp+k6za6xbCW3kDDup6j60AaNFJkUtABRRRQAUUUUAFFFFABRRRQAmBS0UUAJ0FLRRQAUUUUAFFFN3UAOopMiuc8SeMLbRFaNCJrojhAeB9aAL2va/b6HbNJKwMn8MYPJNeU63rdxrl2Z5246Kg6KKh1HU59UuTPcvvc/kKrUAFFFFABSfw0tJ70ALRSfxUtABRRRQAUUnWloAKKKT+dAC0UUUAFFI3SloATqKWk20tACfw0tFFABRSdaNtAC0UUUAA60UDg0UAFFJ1paAE6UtJtpaACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKtadqdzpVws1tIUYEEjsfrVRulW7LSrvUmAtrd5cnGQOKAPQvD/j22vwI7zbbT9Ac/K3+FdWkiyKGVgynoRzmvP9K+G0rlHvpgi4yY4+T+ddtpelxaRaiCEsUHOWOTQBeoopMCgBaKKKACiiigApMilooAQdKMilooATApaKKACkwKWk6igAJxVa9v7ewiMlxKsSDuxqyRmue8Q+D4deYyNNJHLj5ecqPwoA5vxD8QXn32+njbGRgzHqfp6VxckjSuXdi7sclj1Nb+q+B9S035kj+0x+sfUfhWA8bRMVdSrDggigBKKRelLQAUUi9KWgAooooAKKKKACik20bqAFoopOpoAWiiigAopNtLQAn8NLRRQAUn8NLRQAUUUUAFFFIBigBaKKKAAHkUUDqKKACiiigAooooATpS0UUAFFFFABRRRQAUUUUAI3SloooAKKKKACiiigAooooAKKKKACiipLa2lu5VjhjaRzxhRmgCOrmm6LeatKEtoWcd2xwPxrstB+HgRlm1Fg/fyV6fjXbW9tFaxiOGNY4x0VRgUAchovw7gt9sl+/nP18tfuiuwt7WK1iEcKLGg6KoxUuOMUYFACbadRRQAh6UdRR0FGBQAtFFFABRRRQAUUUUAFFFFABSdRS0UAJ1FLRRQAUhGaWigBvFZWreG7HWFPnwgP2kUYatekwKAPLta8A3lhue1/0mH0H3hXLvG0TlXUqw6gjmveNtZOteGrLW4mEsYWUj5ZVHzCgDxtulDdK39e8H3mi/OB58GfvoOn1rAbpQAtJ/FS0UAIvSlopG6UALRRRQAUnQ0tFABRRRQAUUUUAFFFFABRRSbqADdS0UUAI3SjdS0UAFIvSloJoABjNFA6iigAooooAKTbS0UAIvSjdS0UAJ0pTxRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUKrOwVQSScYFdt4V8CNPtutQG1DysPc/WgDE0Dwnd65IGA8q37yt/SvTNH0Cz0SLZbR4J6u3JNX4okgQJGoRRxgCpOgoAMCgDFLSZFAC0h6UtFABSHpS0UAFFFJ2oAWiikHSgA6iloooATj2paKTIoAWkyKWkyKAFoopOooAWiiigAooooAQ9KWkyKWgApoGadSE4oAayBlIIBHoa47xF4ChvN09h+6m5Zo/wCFq7Sm/hQB4VdWk1jM0U8ZjkHBU1HXsmueHbXXINkqhZB92QDkV5brfh+60K4Mcy7oz92RehoAzaKRulBGaAFooooAKQDJpaKACiiigAooooAKKKKAE20N0o60tABRRRQAUUUUAFFFFACDqaKWigAooooAKKRelLQAUUUUAFFI3SloAKKKKACiiigAooooAKKKKACiiigAp8EElzKscal3Y4AFFvbyXUyRRIXkc4AFeo+FvCEWixrNMA92Rz6L9KAKvhPwWmnKt1eKHuTyE6hP/r12GBR2paAEwKWikAxQAtFFFABRRRQAUUUmBQAtFFFABSdRS0nUUALRRRQAUUUUAFFJ0FLQAUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnQUtACYFVdQ0+DUrdoZ0Dowx9Kt0UAeR+JvCc+hSl0BktD0fuPY1z/8AFXut3bR3kDRSqHRhgg15h4s8IvormeAGS0bvjlD70AcyvSloooAKKKKACiiigAooooAKKTrS0AFI3SlooAKRulLRQAUUUUAFFFFAAOoooHUUUAFFFFABRRRQAUUUUAFFJ1paACiiigAooooAKKKKACiiigAzinxRPcSpFGMuxwB60wAsQByT0Fek+CfCa2EK3tyga6b7oP8AAP8AGgC14P8ACq6NB58yhrlx1x90e1dOBxQBxS0AFFFFABRRRQAUUUUAIelLRRQAUUUUAJ1FLRRQAmBS0UUAJgUtIelLQAUUUUAFJ3paKACik7UtABRRRQAUnUUtFACHpS0UnQUALSYFLRQAUUUUAFFFFABUU0KTxNHIodGGCpGQalooA8l8WeFX0OYyxjNqx4P92uer3O8tIr6B4ZkDxsMEGvJPE/h6TQL5lAJt2OY29vQ0AY9Jto3UtACbaWiigBF6UAYpaKACiiigAooooAKKKTdQAtFFFABRRRQADqKKB1ooAKT+KlooAKKKM5oAKKKKAE/hpQaT+KloAKKKKACiiigAooooAKKK2vCvh9tevwD8tvGcu39KANvwJ4XFy32+6TMY/wBUjD7x/vV6IuAMVHBCsESxxgKijAAHAqUdKAFooooAKKKKACiiigAooooAKKKKACk6ClooAKKKKACiiigAoopD0oAWikwKWgBMijIpaKACiikwKAFooooAKKTvS0AFFFFABRRRQAUUUUAFFFFABRRRQAh6VQ1fSYdYs3t5lBBHDd1PrWhSYFAHiGq6ZNpF9JbSjBU8E8bh61Ur1fxh4cXXLHfGALmIZU+vsa8okRo3ZWGGBwR6UAFFFJ/FQAtFFFABRSbaG6UALRRRQAHmiik20ALRRRQAUUUUAA60UDqKKACiiigApOlG6loAKQHNLRQAnvS0UUAFFFJuoAWiiigAooooAltbWS9uEhiUs7nAwK9j0HRo9E02O2Q5IGWb1Nc18PdAWO3/ALRlU+a/EYPYetduBigAAxS0UUAFIOlLRQAUUUUAFFFFABRRRQAUh6UtFABRRRQAUUUUAFJgUtMoAfRRRQAUUynYFAC0U3bTqACiiigAooooAKKKTtQAtFFFABRRRQAUUUUAFFN/GnUAFFFFABRRTKAF21558QfDvkS/2jApKucSADofWvRMCoLq1S8t5IZRuRxtYe1AHhlFaGv6S2i6nLbHOwHKE9xWfQAUUUUABGKKKKAE3UtFJ/DQAEZo/hpaKACiik/ioAWik/hpaAAHmikHVaKAFooooAT8KN1LRQAUUUUAFFFFABRRRQAUUUUAFanhvSG1nVYYcHywdznHassAngda9X8E6GNJ0tJHQC4mG5jjkDsKAN+KJYY1RFCKowABxUtFFABRRRQAUg6UtFABRRRQAnQUtFFACYFLRRQAUUUUAFFFFABRRRQAUUUUAFFFJ3oAWiiigAopOgpaACkPSlooAKKKKACik70tABRSfhS0AFFFFABSDpS0UAJgUtFFABRRRQAUUUUAFNAzS4FLQBy/jnRBqelNNGoM8PzDA5I7ivLK95KhgQQCD1Brybxron9kaszRrtgm+ZQBwPUUAc/SZ5paRelAC0UUjdKADpS0nSloAKKKKAEBzS0UUAJ/DS0nNHSgBR1ooHUUUAFFB60h6tQAtJ/FS0UAFFFFABRRRQAUUUUAFFFJ1oA3PB+jnV9YjDDMMXzuf6V68oC4A4A7VzPgLSxYaMsjIVkn+Y59O1dRQAUh6UtFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUh6UtFABRRRQAUUUUAFFFFABRSE4oyKAFooooAKKKKACiiigBOopaKKACik/GloAToKWiigAoopO9AC0gGKWigAopMiloAKKKTIoAWkPSlooAKwPGGjDWNIkCrmaP50reyKT+GgDwUjaSCORwaWt3xppY03XJdiFYpfnX+tYVACbqWk9aM80ALRRRQAUUnWloAT0oPUUtFABSD0o/hpaAAdaKB1FFACfjRtpaKAE20baF6Ue1AC0UUUAFFFFABRRRQAVoeHtP/tPVreDaWUtlsegrPr0D4a6WUinvnA+f5EyPzoA7eOMRKqKMKowBUlFFABRSdqWgAooooAKKKKACiiigAooooAKKKKACiiigAooooAKQ9KMiloAKKKKAE6CloooATPtS0UmRQAZFLRRQAUUyn0AFJ0FLRQAnej8KWigBD0paKKACiiigApMilooAQdKWiigAooooAKTIpaToKAFoopOooAWkPSlooA5Tx/pYvdI89VJlgO4EenevLyTXutxEJ4XjYAhhg5rxbWLB9M1Ge3cYKsccdu1AFOik7GloAKKKKACiiigAopCM0tABRRRQAg6rRQOpooAWkXpRkUtABRRSL0oAWkXpS0UAFFFFABRRRQA6NGldUUZZjgCvaNDsF0zTLeBV2lVGQPWvMPBunf2hrsCkApH85B9q9fAwOKADtS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUnelpMCgA6ilopOgoAWiikwKAFooooAKKKTAoAMCloooAKKKKACiiigAooooAKKKKACiiigAooooATpmloooAKKKQDFAC0UUUAFIelLTKAH0UUUAJ2rzn4laWIbmC9RcLINjtnuOlej1heMdN/tHQp1ABdBvUn1FAHkC9KP4aWigApP4aWigApNtLSbqAFopN1LQAUUgOaWgBB1WigdVooAP4aN1LRQAi9KWiigAoooHNABRSbaWgAooooA9A+Gdjtgubs/xsEX8Otd3WL4TsjY6BaRn7xXecepraoAKKKKACiiigAopD0paACiikHSgA7UtJ2paACiiigAooooAKKKKACkwKWigApD0paKAEwKWiigAooooAKTApaKACiiigAooooATvTafRQAneloooAKKKKACiikPSgBtPoooAKKKKAEPSlpOopaACiiigApO1LSDpQAtJgUHpS0AFRyoJI2Q9GGKkprdaAPEdXtDYancwH+ByB9KqV1PxEsvs+sLNjCzL29a5agBP4qP4qWk60ALRRScUALRSbqWgBAcUfxUtJ1oAUdaKQYzRQAtFFFABRRSA+tAC0m2looATrS0UUAFWNOgN3f28IGd7har1veCLX7T4hgP8KZc5oA9YijWKNUAwFAAFS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUh6UALRRRQAUUUUAFFFFABRRSYFAC0UUUAFFJkUtABRRRQAUUUUAFFFFABRRRQAUh6UDpS0AFFFJ2oAWik6iloAKKKKAE6iloplAD6QdKWigApvU0uRTaAOO+JVmJNMguAOY3wT7GvOMcV7B4vtPtfh68QfeVN4+orx+gAoopN1AC0UUUAFFFFACL0paKKAAdaKAeRRQAUUhOKWgBN1H9KWigAooooAKKKKAEbpXb/AAytA1zdXBydqhRXE16T8NbcJpM0u3Bd+tAHZUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnaloAKKKKACiiigAooooAKKKQ9KAFooooAKKKTqKAFooooAKKKKAEPSlpB0paAEPSloooAKKKKACiim/w0AOooooAKKQ9KWgAooooAKKKKACiiigApO3rS0UAFFJ2paAEyKWiigApB0paKAIbmITW8kZ6MpBrw25h+zzyRHqjFefavdjyK8Y8SwC2128QLtXzCQPrQBm0UUUAI3SlopAc0ADdKWikzzQAtJ1o20tAAO1FJnHPpRQAtFFFABSfxUbaWgAooooATdS0UUAFes+BYBB4ct/ViWJryUnFezeG4jFoNiOuYlPT1FAGtRRRQAUUUUAFFFFABRRRQAUUUmRQAdBS0UUAFFJ3paACiiigAopD0paACiiigAooooATvS0UUAIelLSE4paACiik70ALRRRQAUUnajIoAMCloooAKKKTtQAtFFFABRRRQAnalpO9HagBaKKKACiiigBPwpaKKACiiigBMiloooAKKKToKAEbrXk/j2AReIpSOA6g16wegrzT4lx7NWt2zndH+XNAHI0UUUAFJ/FR/DS0AJ1o4paKACiiigBD3opaKAG5Wl3UUUAAOaBz6UUUAA6mloooAKQketFFACoRvHPevcNNUQ6fbIWGVjUfpRRQBZ3j1FG4eo/OiigA3j1FG8eooooAN49RRvHqKKKAFyPUUm8eooooAXI9RSbx6iiigA3D1H50uR6iiigBNw9R+dG4eo/OiigA3j1FG8eooooAXI9RRkeooooAMj1FJvHqKKKADePUUbgO9FFABuHqPzo3D1H50UUAG4Z6j86N49RRRQAbx6ijcPUfnRRQAbx6ijePUUUUAG8eoo3D1H50UUAG8eooDD1FFFABvHqKNw9R+dFFABuB7/rRvHqKKKADePUUbx6iiigA3D1H50bx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADcM9RRvHqKKKAAsMdR+def/E5Bvs3yDwRiiigDhdw9aWiigBMijiiigA3UtFFACbqOKKKAA8DtRRRQB//Z";
                    byte[] img = student.getPicture();
                    String imageBase64 = "";
                    if (img != null) {
                        imageBase64 = new String(Base64.getEncoder().encode(img));
                    } else {
                        imageBase64 = image;
                    }
                %>
                <img src="data:image/jpeg;base64, <%=imageBase64%>" class="rounded float-left"
                     style="position:absolute; bottom: 50px" height="200" width="178">

                <input type="file" id="customFile" name="image" onchange="previewFile()" multiple
                       value="data:image/jpeg;base64, <%=imageBase64%>" required>
            </div>
        </div>
        <br>
        <div class="form-row">
            <input type="hidden" name="addressId" value="<%=(studentId!=0)?student.getAddresses().getId():""%>">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Country:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Country" name="country"
                       value="<%=(studentId!=0)?student.getAddresses().getCountry():""%>" required>
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Mail:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Mail" name="mail"
                       value="<%=(studentId!=0)?student.getMail():""%>" required>
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">City:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="City" name="city"
                       value="<%=(studentId!=0)?student.getAddresses().getCity():""%>" required>
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Group:</label>
            </div>
            <div class="col-3">
                <select name="group" class="form-control" required>
                    <option <%=(studentId == 0) ? "hidden" : ""%> selected value="">Select group</option>
                    <% Set<Group> groups = (Set<Group>) request.getAttribute("groups");
                        for (Group group : groups) {
                    %>
                    <option <%=(studentId != 0) ? ((student.getGroup().getId() == group.getId()) ? "selected" : "") : ""%>
                            value="<%=group.getId()%>"><%=group.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Address:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Address" name="address"
                       value="<%=(studentId!=0)?student.getAddresses().getAddress():""%>" required>
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Disciplines:</label>
            </div>
            <div class="col-3">
                <select name="disciplineId[]" class="form-control" style="position:absolute; height: 100px" multiple
                        required>
                    <% Set<Discipline> disciplines = (Set<Discipline>) request.getAttribute("disciplines");
                        ArrayList<Long> studentDisciplineId = new ArrayList<>();
                        if (studentId != 0) {
                            for (Discipline studentDiscipline : student.getDisciplines()) {
                                studentDisciplineId.add(studentDiscipline.getId());
                            }
                        }
                        for (Discipline discipline : disciplines) {
                    %>
                    <option <%=(studentId != 0) ? ((studentDisciplineId.contains(discipline.getId())) ? "selected" : "") : ""%>
                            value="<%=discipline.getId()%>"><%=discipline.getTitle()%>
                    </option>
                    <%}%>
                </select>
            </div>
        </div>
        <br>
    </div>
    <div class="allphones">
        <div class="form-row" name="phone">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Phone(s):</label>
            </div>
            <% Set<Phone> phones = new HashSet<>();
                if (studentId != 0) {
                    phones = (Set<Phone>) student.getPhones();
                }
                ArrayList<Long> phoneTypeId = new ArrayList<>();
                ArrayList<Long> phoneId = new ArrayList<>();
                ArrayList<String> phoneValue = new ArrayList<>();
                int count = 0;
                for (Phone phone : phones) {
                    phoneTypeId.add(phone.getPhoneType().getId());
                    phoneId.add(phone.getId());
                    phoneValue.add(phone.getValue());
                }
                do {%>
            <div class="col-1">
                <select class="form-control" name="phoneType[]" required>
                    <option <%=(studentId == 0) ? "hidden" : ""%> selected>Select phone type</option>
                    <% for (PhoneType phoneType : phoneTypes) {%>
                    <option <%=(studentId != 0) ? ((phoneTypeId.get(count) == phoneType.getId()) ? "selected" : "") : ""%>
                            value="<%=phoneType.getId()%>"><%=phoneType.getName()%>
                    </option>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="col-1.4">
                <input type="hidden" class="form-control" name="phoneId[]"
                       value="<%=(studentId!=0)?phoneId.get(count):""%>">
                <input type="text" class="form-control" placeholder="Phone number" name="phoneNumber[]"
                       value="<%=(studentId!=0)?phoneValue.get(count):""%>" required>
            </div>
            <div class="col-1.5" name="buttons">
                <button type="button" class="btn btn-success" onclick="duplicate(this)">Add</button>
                <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
            </div>
        </div>
        <div class="form-row">
            <div class="col-1">
            </div>
            <%
                    count++;
                }
                while (count < phoneId.size());
            %>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-6"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#editsavestudentform').ajaxForm(function () {
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form>
</body>
</html>
