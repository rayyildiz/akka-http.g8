package $package$.controllers

import javax.inject.{Inject, Singleton}

import $package$.models.{ApplicationInformation, ApplicationStatus}

import scala.concurrent.ExecutionContext

@Singleton
class MiddlewareController @Inject()()(implicit val executionContext: ExecutionContext) {

  def health: ApplicationStatus = ApplicationStatus(status = true, name = "$name;format="norm,word"$")

  def info: ApplicationInformation = ApplicationInformation(version = "$version;format="norm,word"$", name = "$name;format="norm,word"$")

}
