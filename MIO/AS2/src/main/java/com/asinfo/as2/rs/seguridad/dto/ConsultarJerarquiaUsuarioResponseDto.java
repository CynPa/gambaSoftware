/*  1:   */ package com.asinfo.as2.rs.seguridad.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ConsultarJerarquiaUsuarioResponseDto
/*  8:   */   extends ConsultarUsuarioResponseDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private String nombreDocumentoBase;
/* 12:12 */   private List<ConsultarJerarquiaUsuarioResponseDto> listaUsuarios = new ArrayList();
/* 13:   */   
/* 14:   */   public String getNombreDocumentoBase()
/* 15:   */   {
/* 16:15 */     return this.nombreDocumentoBase;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombreDocumentoBase(String nombreDocumentoBase)
/* 20:   */   {
/* 21:19 */     this.nombreDocumentoBase = nombreDocumentoBase;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<ConsultarJerarquiaUsuarioResponseDto> getListaUsuarios()
/* 25:   */   {
/* 26:23 */     return this.listaUsuarios;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setListaUsuarios(List<ConsultarJerarquiaUsuarioResponseDto> listaUsuarios)
/* 30:   */   {
/* 31:27 */     this.listaUsuarios = listaUsuarios;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.ConsultarJerarquiaUsuarioResponseDto
 * JD-Core Version:    0.7.0.1
 */