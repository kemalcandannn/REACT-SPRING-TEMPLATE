import { ERROR_CODE } from "../constants/ErrorCodes";
import { isLabelKey } from "../constants/Labels";
import { PARAMETER_CODE } from "../constants/ParameterCodes";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import { useLanguage } from "../contexts/language/LanguageContext";

export const useApiErrorHandler = () => {
    const { getLabel, getLabelFormatted } = useLanguage();
    const { parameters } = useAuthentication();

    const handleApiError = (err: any): string => {
        if (!err?.response?.data) {
            return getLabel("unknownErrorOccured");
        }

        const { errorCode, errorMessage } = err.response.data;

        switch (errorCode) {
            case ERROR_CODE.TOKEN_EXPIRED:
                return getLabel("tokenHasExpired");
            case ERROR_CODE.RESET_PASSWORD_TOKEN_EXPIRED:
                return getLabel("resetPasswordTokenHasExpired");
            case ERROR_CODE.RESET_PASSWORD_TOKEN_NOT_FOUND:
                return getLabel("resetPasswordTokenNotFound");
            case ERROR_CODE.TOKEN_VALIDATION_ERROR:
                return getLabel("tokenValidationError");
            case ERROR_CODE.REQUIRED_FIELD: {
                const match = errorMessage?.match(/\[(.*?)\]/);
                const value = match ? (isLabelKey(match[1]) ? getLabel(match[1]) : match[1]) : null;
                return value != null
                    ? getLabelFormatted("requiredField", value)
                    : getLabel("checkRequiredField");
            }
            case ERROR_CODE.INCORRECT_USERNAME_OR_PASSWORD:
                return getLabel("incorrectUsernameOrPassword");
            case ERROR_CODE.INCORRECT_OLD_PASSWORD:
                return getLabel("incorrectOldPassword");
            case ERROR_CODE.NEW_PASSWORD_DOES_NOT_CONFIRM:
                return getLabel("newPasswordDoesNotConfirm");
            case ERROR_CODE.PASSWORD_AT_LEAST_CHARACTERS_LONG: {
                const value = parameters?.find(item => item.code == PARAMETER_CODE.PASSWORD_AT_LEAST_CHARACTER_LONG)?.value ?? "8";
                return getLabelFormatted("passwordAtLeastCharactersLong", value);
            }
            case ERROR_CODE.PASSWORD_AT_LEAST_ONE_UPPERCASE:
                return getLabel("passwordAtLeastOneUppercase");
            case ERROR_CODE.PASSWORD_AT_LEAST_ONE_LOWERCASE:
                return getLabel("passwordAtLeastOneLowercase");
            case ERROR_CODE.PASSWORD_AT_LEAST_ONE_DIGIT:
                return getLabel("passwordAtLeastOneDigit");
            case ERROR_CODE.PASSWORD_AT_LEAST_ONE_SPECIAL_CHARACTER:
                return getLabel("passwordAtLeastOneSpecialCharacter");
            case ERROR_CODE.LAST_3_PREVIOUS_PASSWORD_DIFFERENT:
                return getLabel("last3PreviousPasswordDifferent");
            case ERROR_CODE.DO_NOT_HAVE_PERMISSION:
                return getLabel("dontHavePermission");
            case ERROR_CODE.USERNAME_ALREADY_REGISTERED:
                return getLabel("usernameAlreadyRegistered");
            case ERROR_CODE.USED_BY_THE_SYSTEM: {
                const match = errorMessage?.match(/\[(.*?)\]/);
                const value = match ? (isLabelKey(match[1]) ? getLabel(match[1]) : match[1]) : null;
                return value != null
                    ? getLabelFormatted("requiredField", value)
                    : getLabel("checkRequiredField");
            }
            case ERROR_CODE.RECORD_WITH_ID_NOT_FOUND: {
                const match = errorMessage?.match(/\[(.*?)\]/);
                const value = match ? (isLabelKey(match[1]) ? getLabel(match[1]) : match[1]) : null;
                return value != null
                    ? getLabelFormatted("requiredField", value)
                    : getLabel("checkRequiredField");
            }
            default:
                return errorMessage ?? getLabel("unknownErrorOccured");
        }
    };

    return { handleApiError };
};
