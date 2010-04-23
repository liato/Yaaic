/*
Yaaic - Yet Another Android IRC Client

Copyright 2009-2010 Sebastian Kaspari

This file is part of Yaaic.

Yaaic is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Yaaic is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Yaaic.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.yaaic.command.handler;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import org.yaaic.command.BaseHandler;
import org.yaaic.exception.CommandException;
import org.yaaic.irc.IRCService;
import org.yaaic.model.Broadcast;
import org.yaaic.model.Conversation;
import org.yaaic.model.Message;
import org.yaaic.model.Server;

import android.content.Intent;

/**
 * Command: /script <code>
 * 
 * Executes JavaScript Code.
 * There are some special objects and functions you can use to interact with
 * Yaaic. See the official documentation for more:
 *   http://www.yaaic.org/documentation/
 * 
 * @author Sebastian Kaspari <sebastian@yaaic.org>
 */
public class ScriptHandler extends BaseHandler
{
	private static Context scriptContext;
	private static Scriptable scope;
	
	/**
	 * Execute /script
	 */
	@Override
	public void execute(String[] params, Server server, Conversation conversation, IRCService service) throws CommandException 
	{
		try {
			String script = BaseHandler.mergeParams(params);

			if (scriptContext == null) {
				scriptContext = Context.enter();
		        scriptContext.setOptimizationLevel(-1);
				scope = scriptContext.initStandardObjects();
			}
			Object result = scriptContext.evaluateString(scope, script, "<cmd>", 1, null);

			conversation.addMessage(new Message("> " + script));
			conversation.addMessage(new Message("Result: " + Context.toString(result)));

			Intent intent = Broadcast.createConversationIntent(
				Broadcast.CONVERSATION_MESSAGE,
				server.getId(),
				conversation.getName()
			);

			service.sendBroadcast(intent);
		} catch (Exception e) {
			Message message = new Message("Error: " + e.getMessage());
			message.setColor(Message.COLOR_RED);
			conversation.addMessage(message);

			Intent intent = Broadcast.createConversationIntent(
				Broadcast.CONVERSATION_MESSAGE,
				server.getId(),
				conversation.getName()
			);

			service.sendBroadcast(intent);
		}
	}
	
	/**
	 * Usage of /me
	 */
	@Override
	public String getUsage()
	{
		return "/script <code>";
	}

	/**
	 * Description of /script
	 */
	@Override
	public String getDescription()
	{
		return "Run a script";
	}
}
