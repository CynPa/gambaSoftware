/*  1:   */ package com.asinfo.as2.utils.theme;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Tema;
/*  4:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  5:   */ import com.asinfo.as2.servicio.ServicioTema;
/*  6:   */ import com.asinfo.as2.util.AppUtil;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.annotation.PostConstruct;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.faces.bean.ManagedBean;
/* 11:   */ import javax.faces.bean.ViewScoped;
/* 12:   */ 
/* 13:   */ @ManagedBean
/* 14:   */ @ViewScoped
/* 15:   */ public class ThemeSwitcherBean
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private ServicioTema servicioTema;
/* 19:   */   private List<Tema> listaTema;
/* 20:   */   private String tema;
/* 21:   */   
/* 22:   */   @PostConstruct
/* 23:   */   public void init()
/* 24:   */   {
/* 25:44 */     if (!AppUtil.getUsuarioEnSesion().getNombreUsuario().equals("usuario_anonimo")) {
/* 26:45 */       this.tema = AppUtil.getUsuarioEnSesion().getTema();
/* 27:   */     } else {
/* 28:47 */       this.tema = "redmond";
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public List<Tema> getListaTema()
/* 33:   */   {
/* 34:57 */     if (this.listaTema == null) {
/* 35:58 */       this.listaTema = this.servicioTema.obtenerListaCombo("nombre", true, null);
/* 36:   */     }
/* 37:61 */     return this.listaTema;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getTema()
/* 41:   */   {
/* 42:69 */     return this.tema;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setTema(String tema)
/* 46:   */   {
/* 47:77 */     this.tema = tema;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setListaTema(List<Tema> listaTema)
/* 51:   */   {
/* 52:85 */     this.listaTema = listaTema;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.theme.ThemeSwitcherBean
 * JD-Core Version:    0.7.0.1
 */