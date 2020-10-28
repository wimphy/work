export class MailResponse {
  public value: MailValue[];
}

export class MailValue {
  public id: string;
  public subject: string;
  public bodyPreview: string;
  public sentDateTime: string;
  public body: MailBody;
}

export class MailBody {
  public content: string;
}

export class AttachmentResponse {
  public value: AttachmentValue[];
}

export class AttachmentValue {
  public id: string;
  public contentId: string;
  public contentType: string;
  public contentBytes: string;
}
