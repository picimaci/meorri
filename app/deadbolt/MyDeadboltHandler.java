/*
 * Copyright 2012 Steve Chaloner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package deadbolt;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import controllers.DTOs.core.SysUserDTO;
import daos.SysUserDAO;
import models.SysUser;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import utils.AppConf;
import utils.SessionStoreAdapter;
import views.html.index;
import views.html.login;

import java.util.Optional;

@Transactional
public class MyDeadboltHandler extends AbstractDeadboltHandler {

    @Override
    public F.Promise<Optional<Result>> beforeAuthCheck(Http.Context context) {
        // returning null means that everything is OK. Return a real result if you want a redirect to a login page or
        // somewhere else
        return F.Promise.pure(Optional.empty());
    }

    @Override
    public F.Promise<Optional<Subject>> getSubject(Http.Context context) {
        if(AppConf.getConfigBoolean(AppConf.CONFIG_DEADBOLT_ENABLED)) {
            SysUserDTO sysUserDTO;
            String sessionId = context.session().get("sessionId");
            try {
                sysUserDTO = SessionStoreAdapter.getInstance().getUserSession(sessionId);
            } catch(Exception e) {
                Optional<SysUser> model = SysUserDAO.findBySessionId(sessionId);
                if(!model.isPresent()){
                    return F.Promise.pure(Optional.ofNullable(null));
                }
                return F.Promise.pure(Optional.ofNullable(SysUserDTO.createFromModel(model.get())));
            }
            return F.Promise.pure(Optional.ofNullable(sysUserDTO));
        } else {
            return F.Promise.pure(Optional.of(new SysUserDTO()));
        }
    }

    @Override
    public F.Promise<Result> onAuthFailure(Http.Context context, String content) {
        // you can return any result from here - forbidden, etc
        final Http.Context c = context;
        return F.Promise.promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                String path = c.request().path();
                String sessionId = context.session().get("sessionId");
                try {
                    SessionStoreAdapter.getInstance().getUserSession(sessionId);
                    c.flash().put("error", Messages.get("global.error.noauthorizationforthispage"));
                    return ok(index.render());
                } catch(Exception e) {
                    return ok(login.render(path));
                }
            }
        });
    }
}
