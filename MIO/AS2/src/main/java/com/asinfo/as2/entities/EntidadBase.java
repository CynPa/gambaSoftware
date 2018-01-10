/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.EntityAuditorListenerAS2;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.EntityListeners;
/*  10:    */ import javax.persistence.MappedSuperclass;
/*  11:    */ import javax.persistence.Transient;
/*  12:    */ 
/*  13:    */ @MappedSuperclass
/*  14:    */ @EntityListeners({EntityAuditorListenerAS2.class})
/*  15:    */ public abstract class EntidadBase
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -4493183957890075592L;
/*  19:    */   @Column(name="fecha_creacion", insertable=true, updatable=false, nullable=true)
/*  20:    */   protected Date fechaCreacion;
/*  21:    */   @Column(name="usuario_creacion", insertable=true, updatable=false, nullable=true, length=50)
/*  22:    */   protected String usuarioCreacion;
/*  23:    */   @Column(name="fecha_modificacion", insertable=false, updatable=true, nullable=true)
/*  24:    */   protected Date fechaModificacion;
/*  25:    */   @Column(name="usuario_modificacion", insertable=false, updatable=true, nullable=true, length=50)
/*  26:    */   protected String usuarioModificacion;
/*  27:    */   @Transient
/*  28:    */   protected int id;
/*  29:    */   @Transient
/*  30:    */   protected int idHashCode;
/*  31:    */   @Transient
/*  32: 58 */   protected boolean auditable = true;
/*  33:    */   @Transient
/*  34:    */   protected boolean error;
/*  35:    */   @Transient
/*  36: 64 */   protected boolean editado = false;
/*  37:    */   @Transient
/*  38: 67 */   protected boolean soloLectura = false;
/*  39:    */   @Transient
/*  40: 73 */   protected boolean eliminado = false;
/*  41:    */   
/*  42:    */   public Date getFechaCreacion()
/*  43:    */   {
/*  44: 82 */     return this.fechaCreacion;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setFechaCreacion(Date fechaCreacion)
/*  48:    */   {
/*  49: 92 */     this.fechaCreacion = fechaCreacion;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getUsuarioCreacion()
/*  53:    */   {
/*  54:101 */     return this.usuarioCreacion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setUsuarioCreacion(String usuarioCreacion)
/*  58:    */   {
/*  59:111 */     this.usuarioCreacion = usuarioCreacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Date getFechaModificacion()
/*  63:    */   {
/*  64:120 */     return this.fechaModificacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setFechaModificacion(Date fechaModificacion)
/*  68:    */   {
/*  69:130 */     this.fechaModificacion = fechaModificacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getUsuarioModificacion()
/*  73:    */   {
/*  74:139 */     return this.usuarioModificacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setUsuarioModificacion(String usuarioModificacion)
/*  78:    */   {
/*  79:149 */     this.usuarioModificacion = usuarioModificacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static long getSerialversionuid()
/*  83:    */   {
/*  84:158 */     return -4493183957890075592L;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public abstract int getId();
/*  88:    */   
/*  89:    */   public void setId(int id)
/*  90:    */   {
/*  91:175 */     this.id = id;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isEditado()
/*  95:    */   {
/*  96:184 */     return this.editado;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setEditado(boolean editado)
/* 100:    */   {
/* 101:194 */     this.editado = editado;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isEliminado()
/* 105:    */   {
/* 106:203 */     return this.eliminado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setEliminado(boolean eliminado)
/* 110:    */   {
/* 111:213 */     this.eliminado = eliminado;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isAuditable()
/* 115:    */   {
/* 116:217 */     return this.auditable;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setAuditable(boolean auditable)
/* 120:    */   {
/* 121:221 */     this.auditable = auditable;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getMensageAuditoria()
/* 125:    */   {
/* 126:225 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getClavePrimaria()
/* 130:    */   {
/* 131:229 */     return "id";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<String> getCamposAuditables()
/* 135:    */   {
/* 136:239 */     return new ArrayList();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isError()
/* 140:    */   {
/* 141:243 */     return this.error;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setError(boolean error)
/* 145:    */   {
/* 146:247 */     this.error = error;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean equals(Object entidad)
/* 150:    */   {
/* 151:253 */     if (this == entidad) {
/* 152:254 */       return true;
/* 153:    */     }
/* 154:257 */     return getId() == ((EntidadBase)entidad).getId();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int hashCode()
/* 158:    */   {
/* 159:263 */     return getId() > 0 ? getId() : super.hashCode();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getIdHashCode()
/* 163:    */   {
/* 164:267 */     return hashCode();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getRowKey()
/* 168:    */   {
/* 169:271 */     return getId() == 0 ? super.hashCode() : getId();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isSoloLectura()
/* 173:    */   {
/* 174:275 */     return this.soloLectura;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setSoloLectura(boolean soloLectura)
/* 178:    */   {
/* 179:279 */     this.soloLectura = soloLectura;
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EntidadBase
 * JD-Core Version:    0.7.0.1
 */