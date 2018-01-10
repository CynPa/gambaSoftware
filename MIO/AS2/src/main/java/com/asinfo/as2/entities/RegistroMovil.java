/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="registro_movil")
/*  20:    */ public class RegistroMovil
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="registro_movil", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="registro_movil")
/*  28:    */   @Column(name="id_registro_movil", unique=true, nullable=false)
/*  29:    */   private int idRegistroMovil;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo_movil", nullable=false, length=200)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=200)
/*  37:    */   private String codigoMovil;
/*  38:    */   @Column(name="fecha", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Date fecha;
/*  41:    */   @Column(name="documento_base", nullable=false)
/*  42:    */   @Enumerated(EnumType.ORDINAL)
/*  43:    */   @NotNull
/*  44:    */   private DocumentoBase documentoBase;
/*  45:    */   @Column(name="numero_as2", nullable=true, length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String numeroAs2;
/*  48:    */   @Column(name="nota", nullable=true, length=5000)
/*  49:    */   @Size(max=5000)
/*  50:    */   private String nota;
/*  51:    */   @Column(name="usuario", nullable=true, length=50)
/*  52:    */   @Size(max=50)
/*  53:    */   private String usuario;
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 81 */     return this.idRegistroMovil;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdRegistroMovil()
/*  61:    */   {
/*  62: 85 */     return this.idRegistroMovil;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdRegistroMovil(int idRegistroMovil)
/*  66:    */   {
/*  67: 89 */     this.idRegistroMovil = idRegistroMovil;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72: 93 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77: 97 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:101 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:105 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigoMovil()
/*  91:    */   {
/*  92:109 */     return this.codigoMovil;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigoMovil(String codigoMovil)
/*  96:    */   {
/*  97:113 */     this.codigoMovil = codigoMovil;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Date getFecha()
/* 101:    */   {
/* 102:117 */     return this.fecha;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFecha(Date fecha)
/* 106:    */   {
/* 107:121 */     this.fecha = fecha;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public DocumentoBase getDocumentoBase()
/* 111:    */   {
/* 112:125 */     return this.documentoBase;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 116:    */   {
/* 117:129 */     this.documentoBase = documentoBase;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getNumeroAs2()
/* 121:    */   {
/* 122:133 */     return this.numeroAs2;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setNumeroAs2(String numeroAs2)
/* 126:    */   {
/* 127:137 */     this.numeroAs2 = numeroAs2;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getNota()
/* 131:    */   {
/* 132:141 */     return this.nota;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setNota(String nota)
/* 136:    */   {
/* 137:145 */     this.nota = nota;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getUsuario()
/* 141:    */   {
/* 142:149 */     return this.usuario;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setUsuario(String usuario)
/* 146:    */   {
/* 147:153 */     this.usuario = usuario;
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RegistroMovil
 * JD-Core Version:    0.7.0.1
 */