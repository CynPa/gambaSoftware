/*   1:    */ package com.asinfo.as2.ws.seguridad.model;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*   4:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   5:    */ import com.asinfo.as2.seguridad.modelo.Rol;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class UsuarioWSEntity
/*  10:    */ {
/*  11:    */   private int idUsuario;
/*  12:    */   private String nombreUsuario;
/*  13:    */   private String nombre1;
/*  14:    */   private String nombre2;
/*  15:    */   private String tema;
/*  16: 34 */   private List<Rol> listaRol = new ArrayList();
/*  17:    */   
/*  18:    */   public UsuarioWSEntity(EntidadUsuario entidadUsuario)
/*  19:    */   {
/*  20: 38 */     this.idUsuario = entidadUsuario.getIdUsuario();
/*  21: 39 */     this.nombreUsuario = entidadUsuario.getNombreUsuario();
/*  22: 40 */     this.nombre1 = entidadUsuario.getNombre1();
/*  23: 41 */     this.nombre2 = entidadUsuario.getNombre2();
/*  24: 42 */     this.tema = entidadUsuario.getTema();
/*  25: 44 */     for (EntidadRol er : entidadUsuario.getListaRol()) {
/*  26: 45 */       agregarRol(new Rol(er));
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public UsuarioWSEntity(String nombreUsuario)
/*  31:    */   {
/*  32: 50 */     this.nombreUsuario = nombreUsuario;
/*  33: 51 */     this.idUsuario = 0;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public UsuarioWSEntity() {}
/*  37:    */   
/*  38:    */   public void agregarRol(Rol rol)
/*  39:    */   {
/*  40: 58 */     this.listaRol.add(rol);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getIdUsuario()
/*  44:    */   {
/*  45: 67 */     return this.idUsuario;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdUsuario(int idUsuario)
/*  49:    */   {
/*  50: 77 */     this.idUsuario = idUsuario;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getNombreUsuario()
/*  54:    */   {
/*  55: 86 */     return this.nombreUsuario;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setNombreUsuario(String nombreUsuario)
/*  59:    */   {
/*  60: 96 */     this.nombreUsuario = nombreUsuario;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNombre1()
/*  64:    */   {
/*  65:105 */     return this.nombre1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setNombre1(String nombre1)
/*  69:    */   {
/*  70:115 */     this.nombre1 = nombre1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getNombre2()
/*  74:    */   {
/*  75:124 */     return this.nombre2;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNombre2(String nombre2)
/*  79:    */   {
/*  80:134 */     this.nombre2 = nombre2;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<Rol> getListaRol()
/*  84:    */   {
/*  85:143 */     return this.listaRol;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setListaRol(List<Rol> listaRol)
/*  89:    */   {
/*  90:153 */     this.listaRol.clear();
/*  91:154 */     this.listaRol.addAll(listaRol);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getTema()
/*  95:    */   {
/*  96:163 */     return this.tema;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setTema(String tema)
/* 100:    */   {
/* 101:173 */     this.tema = tema;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.seguridad.model.UsuarioWSEntity
 * JD-Core Version:    0.7.0.1
 */