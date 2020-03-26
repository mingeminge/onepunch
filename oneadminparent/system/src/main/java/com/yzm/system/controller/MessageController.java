package com.yzm.system.controller;

import com.yzm.common.util.CopyUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.system.controller.vo.MessageVO;
import com.yzm.system.entity.Message;
import com.yzm.system.entity.User;
import com.yzm.system.entity.UserMessage;
import com.yzm.system.mapper.MessageMapper;
import com.yzm.system.mapper.UserMapper;
import com.yzm.system.mapper.UserMessageMapper;
import com.yzm.system.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:13 2020/2/10
 * ===========================
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    private UserMessageMapper userMessageMapper;

    private UserMapper userMapper;

    private MessageMapper messageMapper;

    @Autowired
    public MessageController(MessageService messageService, UserMessageMapper userMessageMapper, UserMapper userMapper,MessageMapper messageMapper) {
        this.messageService = messageService;
        this.userMessageMapper = userMessageMapper;
        this.userMapper = userMapper;
        this.messageMapper=messageMapper;
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Message message) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User byUsername = userMapper.findByUsername(username);
        message.setTime(LocalDateTime.now());
        messageMapper.setUTF8MB4();
        messageService.save(message);

        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(byUsername.getId());
        userMessage.setMessageId(message.getId());
        userMessageMapper.insert(userMessage);
        return ResultVO.ok();
    }

    @GetMapping("/list")
    public PageVO messageList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        List<UserMessage> userMessageEntities = userMessageMapper.selectList(null);

        List<MessageVO> messageVOList = new ArrayList<>();
        userMessageEntities.forEach(userMessage -> {
            Message byId = messageService.getById(userMessage.getMessageId());
            MessageVO messageVO = CopyUtil.copyProperties(byId, MessageVO.class);

            User byUserId = userMapper.findByUserId(userMessage.getUserId());

            messageVO.setAvatar(byUserId.getAvatar());
            messageVO.setUsername(byUserId.getUsername());

            messageVOList.add(messageVO);

        });

        List<MessageVO> messageVOS = messageVOList.stream().sorted(Comparator.comparing(messageVO -> messageVO.getTime(), Comparator.reverseOrder())).collect(Collectors.toList());
        if (messageVOS.size() > 0) {
            return PageVO.build(Long.valueOf(messageVOS.size()), messageVOS);
        }
        return PageVO.build(0L, null);
    }
}
