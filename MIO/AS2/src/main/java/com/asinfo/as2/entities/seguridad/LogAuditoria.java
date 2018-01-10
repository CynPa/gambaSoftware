/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.ProcesoAuditoriaEnum;
/*   5:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.Lob;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="log_auditoria")
/*  21:    */ public class LogAuditoria
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="log_auditoria", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="log_auditoria")
/*  28:    */   @Column(name="id_log_auditoria", nullable=false, updatable=false)
/*  29:    */   private Long idLogAuditoria;
/*  30:    */   @Column(name="id_organizacion", nullable=false, updatable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_entidad", nullable=false, updatable=false)
/*  33:    */   private Long idEntidad;
/*  34:    */   @Column(name="entidad", nullable=false, updatable=false, length=50)
/*  35:    */   private String claseEntidad;
/*  36:    */   @Column(name="referencia")
/*  37:    */   private String referencia;
/*  38:    */   @Lob
/*  39:    */   @Column(name="mensaje", nullable=false, updatable=false)
/*  40:    */   private String mensaje;
/*  41:    */   @Column(name="id_usuario", nullable=false, updatable=false)
/*  42:    */   private Long idUsuario;
/*  43:    */   @Column(name="nombre_usuario", nullable=false, updatable=false, length=50)
/*  44:    */   private String nombreUsuario;
/*  45:    */   @Column(name="host_remoto", nullable=true, updatable=false, length=200)
/*  46:    */   private String hostRemoto;
/*  47:    */   @Enumerated(EnumType.STRING)
/*  48:    */   @Column(name="proceso_auditoria", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private ProcesoAuditoriaEnum procesoAuditoria;
/*  51:    */   
/*  52:    */   public LogAuditoria() {}
/*  53:    */   
/*  54:    */   public LogAuditoria(Long idEntidad, String claseEntidad, String mensaje, Usuario usuario, String hostRemoto)
/*  55:    */   {
/*  56: 87 */     this.idLogAuditoria = Long.valueOf(0L);
/*  57: 88 */     this.idEntidad = idEntidad;
/*  58: 89 */     this.claseEntidad = claseEntidad;
/*  59: 90 */     this.fechaCreacion = new Date();
/*  60: 91 */     this.mensaje = mensaje;
/*  61: 92 */     this.idUsuario = Long.valueOf(usuario.getIdUsuario());
/*  62: 93 */     this.nombreUsuario = usuario.getNombreUsuario();
/*  63: 94 */     this.hostRemoto = hostRemoto;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Long getIdEntidad()
/*  67:    */   {
/*  68:104 */     return this.idEntidad;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdEntidad(Long idEntidad)
/*  72:    */   {
/*  73:114 */     this.idEntidad = idEntidad;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getClaseEntidad()
/*  77:    */   {
/*  78:123 */     return this.claseEntidad;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setClaseEntidad(String claseEntidad)
/*  82:    */   {
/*  83:133 */     this.claseEntidad = claseEntidad;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getReferencia()
/*  87:    */   {
/*  88:142 */     return this.referencia;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setReferencia(String referencia)
/*  92:    */   {
/*  93:152 */     this.referencia = referencia;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getMensaje()
/*  97:    */   {
/*  98:161 */     return this.mensaje;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setMensaje(String mensaje)
/* 102:    */   {
/* 103:171 */     this.mensaje = mensaje;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Long getIdUsuario()
/* 107:    */   {
/* 108:175 */     return this.idUsuario;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdUsuario(Long idUsuario)
/* 112:    */   {
/* 113:179 */     this.idUsuario = idUsuario;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Long getIdLogAuditoria()
/* 117:    */   {
/* 118:183 */     return this.idLogAuditoria;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdLogAuditoria(Long idLogAuditoria)
/* 122:    */   {
/* 123:187 */     this.idLogAuditoria = idLogAuditoria;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getNombreUsuario()
/* 127:    */   {
/* 128:191 */     return this.nombreUsuario;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNombreUsuario(String nombreUsuario)
/* 132:    */   {
/* 133:195 */     this.nombreUsuario = nombreUsuario;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getHostRemoto()
/* 137:    */   {
/* 138:199 */     return this.hostRemoto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setHostRemoto(String hostRemoto)
/* 142:    */   {
/* 143:203 */     this.hostRemoto = hostRemoto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getIdOrganizacion()
/* 147:    */   {
/* 148:207 */     return this.idOrganizacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setIdOrganizacion(int idOrganizacion)
/* 152:    */   {
/* 153:211 */     this.idOrganizacion = idOrganizacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public ProcesoAuditoriaEnum getProcesoAuditoria()
/* 157:    */   {
/* 158:215 */     return this.procesoAuditoria;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setProcesoAuditoria(ProcesoAuditoriaEnum procesoAuditoria)
/* 162:    */   {
/* 163:219 */     this.procesoAuditoria = procesoAuditoria;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getId()
/* 167:    */   {
/* 168:224 */     return 0;
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.LogAuditoria
 * JD-Core Version:    0.7.0.1
 */