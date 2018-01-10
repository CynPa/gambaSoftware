/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ 
/*  11:    */ @Entity
/*  12:    */ @Table(name="mensaje_email")
/*  13:    */ public class MensajeEmail
/*  14:    */   extends EntidadBase
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="mensaje_email", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="mensaje_email")
/*  20:    */   @Column(name="id_mensaje_email")
/*  21:    */   private int idMensajeEmail;
/*  22:    */   @Column(name="id_organizacion", nullable=false)
/*  23:    */   private int idOrganizacion;
/*  24:    */   @Column(name="para", length=1000, nullable=true)
/*  25:    */   private String para;
/*  26:    */   @Column(name="titulo_mensaje", nullable=true, columnDefinition="text")
/*  27:    */   private String tituloMensaje;
/*  28:    */   @Column(name="cuerpo_mensaje", nullable=true, columnDefinition="text")
/*  29:    */   private String cuerpoMensaje;
/*  30:    */   @Column(name="archivos_adjuntos", nullable=true, columnDefinition="text")
/*  31:    */   private String archivosAdjuntos;
/*  32:    */   @Column(name="mapa_cid", nullable=true, columnDefinition="text")
/*  33:    */   private String mapaCid;
/*  34:    */   @Column(name="adjunto_byte", nullable=true, length=5242880)
/*  35:    */   private byte[] adjuntoByte;
/*  36:    */   @Column(name="nombre_adjunto_byte", nullable=true)
/*  37:    */   private String nombreAdjuntoByte;
/*  38:    */   @Column(name="tipo_adjunto_byte", nullable=true)
/*  39:    */   private String tipoAdjuntoByte;
/*  40:    */   @Column(name="indicador_enviado")
/*  41:    */   private boolean indicadorEnviado;
/*  42:    */   
/*  43:    */   public MensajeEmail() {}
/*  44:    */   
/*  45:    */   public MensajeEmail(int idOrganizacion, String para, String tituloMensaje, String cuerpoMensaje, String archivosAdjuntos, String mapaCid, byte[] adjuntoByte, String nombreAdjuntoByte, String tipoAdjuntoByte)
/*  46:    */   {
/*  47: 79 */     this.idOrganizacion = idOrganizacion;
/*  48: 80 */     this.para = para;
/*  49: 81 */     this.tituloMensaje = tituloMensaje;
/*  50: 82 */     this.cuerpoMensaje = cuerpoMensaje;
/*  51: 83 */     this.archivosAdjuntos = archivosAdjuntos;
/*  52: 84 */     this.mapaCid = mapaCid;
/*  53: 85 */     this.adjuntoByte = adjuntoByte;
/*  54: 86 */     this.nombreAdjuntoByte = nombreAdjuntoByte;
/*  55: 87 */     this.tipoAdjuntoByte = tipoAdjuntoByte;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 92 */     return this.idMensajeEmail;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdMensajeEmail()
/*  64:    */   {
/*  65: 96 */     return this.idMensajeEmail;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdMensajeEmail(int idMensajeEmail)
/*  69:    */   {
/*  70:100 */     this.idMensajeEmail = idMensajeEmail;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:104 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:108 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getPara()
/*  84:    */   {
/*  85:112 */     return this.para;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setPara(String para)
/*  89:    */   {
/*  90:116 */     this.para = para;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getTituloMensaje()
/*  94:    */   {
/*  95:120 */     return this.tituloMensaje;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setTituloMensaje(String tituloMensaje)
/*  99:    */   {
/* 100:124 */     this.tituloMensaje = tituloMensaje;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getCuerpoMensaje()
/* 104:    */   {
/* 105:128 */     return this.cuerpoMensaje;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setCuerpoMensaje(String cuerpoMensaje)
/* 109:    */   {
/* 110:132 */     this.cuerpoMensaje = cuerpoMensaje;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getArchivosAdjuntos()
/* 114:    */   {
/* 115:136 */     return this.archivosAdjuntos;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setArchivosAdjuntos(String archivosAdjuntos)
/* 119:    */   {
/* 120:140 */     this.archivosAdjuntos = archivosAdjuntos;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static long getSerialversionuid()
/* 124:    */   {
/* 125:144 */     return 1L;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isIndicadorEnviado()
/* 129:    */   {
/* 130:148 */     return this.indicadorEnviado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIndicadorEnviado(boolean indicadorEnviado)
/* 134:    */   {
/* 135:152 */     this.indicadorEnviado = indicadorEnviado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getMapaCid()
/* 139:    */   {
/* 140:156 */     return this.mapaCid;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setMapaCid(String mapaCid)
/* 144:    */   {
/* 145:160 */     this.mapaCid = mapaCid;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public byte[] getAdjuntoByte()
/* 149:    */   {
/* 150:164 */     return this.adjuntoByte;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setAdjuntoByte(byte[] adjuntoByte)
/* 154:    */   {
/* 155:168 */     this.adjuntoByte = adjuntoByte;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getNombreAdjuntoByte()
/* 159:    */   {
/* 160:172 */     return this.nombreAdjuntoByte;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setNombreAdjuntoByte(String nombreAdjuntoByte)
/* 164:    */   {
/* 165:176 */     this.nombreAdjuntoByte = nombreAdjuntoByte;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getTipoAdjuntoByte()
/* 169:    */   {
/* 170:180 */     return this.tipoAdjuntoByte;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setTipoAdjuntoByte(String tipoAdjuntoByte)
/* 174:    */   {
/* 175:184 */     this.tipoAdjuntoByte = tipoAdjuntoByte;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MensajeEmail
 * JD-Core Version:    0.7.0.1
 */