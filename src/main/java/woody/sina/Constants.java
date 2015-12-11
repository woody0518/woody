/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package woody.sina;

/**
 * ���ඨ����΢����Ȩʱ����Ҫ�Ĳ���
 * 
 * @author SINA
 * @since 2013-09-29
 */
public interface Constants {

    /** ��ǰ DEMO Ӧ�õ� APP_KEY������Ӧ��Ӧ��ʹ���Լ��� APP_KEY �滻�� APP_KEY */
    String APP_KEY = "3565495486";

    /** 
     * ��ǰ DEMO Ӧ�õĻص�ҳ������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
     * 
     * <p>
     * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ�ģ����Զ���Ϊ������ʽ������Ӱ�죬
     * ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
     * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
     * </p>
     */
    String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ������ͨ�� Scope��ƽ̨�����Ÿ���΢��
     * ���Ĺ��ܸ��ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ��
     * ѡ����Ӧ�õĹ��ܡ�
     * 
     * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ��?���ܿ�������Ŀǰ������Щ�ӿڵ�
     * ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
     * 
     * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
     * 
     * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
     */
    String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}
