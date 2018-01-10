/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.EnumType;
/*   7:    */ import javax.persistence.Enumerated;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="motivo_anulacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  18:    */ public class MotivoAnulacion
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="motivo_anulacion", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_anulacion")
/*  25:    */   @Column(name="id_motivo_anulacion")
/*  26:    */   private int idMotivoAnulacion;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", length=10, nullable=false)
/*  32:    */   @NotNull
/*  33:    */   @Size(max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", length=50, nullable=false)
/*  36:    */   @NotNull
/*  37:    */   @Size(max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="documento_base", nullable=false)
/*  40:    */   @Enumerated(EnumType.ORDINAL)
/*  41:    */   private DocumentoBase documentoBase;
/*  42:    */   @Column(name="descripcion", length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private boolean predeterminado;
/*  51:    */   
/*  52:    */   public MotivoAnulacion() {}
/*  53:    */   
/*  54:    */   public MotivoAnulacion(int idMotivoAnulacion, String codigo, String nombre)
/*  55:    */   {
/*  56: 88 */     this.idMotivoAnulacion = idMotivoAnulacion;
/*  57: 89 */     this.codigo = codigo;
/*  58: 90 */     this.nombre = nombre;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getId()
/*  62:    */   {
/*  63: 95 */     return this.idMotivoAnulacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdMotivoAnulacion()
/*  67:    */   {
/*  68:104 */     return this.idMotivoAnulacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdMotivoAnulacion(int idMotivoAnulacion)
/*  72:    */   {
/*  73:114 */     this.idMotivoAnulacion = idMotivoAnulacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdOrganizacion()
/*  77:    */   {
/*  78:123 */     return this.idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83:133 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:142 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:152 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getCodigo()
/*  97:    */   {
/*  98:161 */     return this.codigo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCodigo(String codigo)
/* 102:    */   {
/* 103:171 */     this.codigo = codigo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getNombre()
/* 107:    */   {
/* 108:180 */     return this.nombre;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setNombre(String nombre)
/* 112:    */   {
/* 113:190 */     this.nombre = nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescripcion()
/* 117:    */   {
/* 118:199 */     return this.descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescripcion(String descripcion)
/* 122:    */   {
/* 123:209 */     this.descripcion = descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isActivo()
/* 127:    */   {
/* 128:218 */     return this.activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setActivo(boolean activo)
/* 132:    */   {
/* 133:228 */     this.activo = activo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isPredeterminado()
/* 137:    */   {
/* 138:237 */     return this.predeterminado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setPredeterminado(boolean predeterminado)
/* 142:    */   {
/* 143:247 */     this.predeterminado = predeterminado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DocumentoBase getDocumentoBase()
/* 147:    */   {
/* 148:251 */     return this.documentoBase;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 152:    */   {
/* 153:255 */     this.documentoBase = documentoBase;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String toString()
/* 157:    */   {
/* 158:260 */     return this.nombre;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoAnulacion
 * JD-Core Version:    0.7.0.1
 */