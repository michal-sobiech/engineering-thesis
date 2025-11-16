export interface LogInOutcomeSuccess {
    status: "SUCCESS",
    jwt: string,
}

export interface LogInOutcomeBadCredentials {
    status: "BAD_CREDENTIALS",
}

export type LogInOutcome = LogInOutcomeSuccess | LogInOutcomeBadCredentials;