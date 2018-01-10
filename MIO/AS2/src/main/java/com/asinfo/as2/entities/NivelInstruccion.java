/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Size;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="nivel_instruccion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class NivelInstruccion
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -3970935530531567904L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="nivel_instruccion", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="nivel_instruccion")
/*  21:    */   @Column(name="id_nivel_instruccion")
/*  22:    */   private int idNivelInstruccion;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="codigo", nullable=false, length=10)
/*  28:    */   @Size(min=1, max=10)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false, length=50)
/*  31:    */   @Size(min=3, max=50)
/*  32:    */   private String nombre;
/*  33:    */   @Column(name="descripcion", length=200, nullable=true)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 79 */     return this.idNivelInstruccion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdNivelInstruccion()
/*  47:    */   {
/*  48: 92 */     return this.idNivelInstruccion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdNivelInstruccion(int idNivelInstruccion)
/*  52:    */   {
/*  53:102 */     this.idNivelInstruccion = idNivelInstruccion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58:111 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63:121 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68:130 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73:140 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCodigo()
/*  77:    */   {
/*  78:149 */     return this.codigo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCodigo(String codigo)
/*  82:    */   {
/*  83:159 */     this.codigo = codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombre()
/*  87:    */   {
/*  88:168 */     return this.nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNombre(String nombre)
/*  92:    */   {
/*  93:178 */     this.nombre = nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getDescripcion()
/*  97:    */   {
/*  98:187 */     return this.descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDescripcion(String descripcion)
/* 102:    */   {
/* 103:197 */     this.descripcion = descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isActivo()
/* 107:    */   {
/* 108:206 */     return this.activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setActivo(boolean activo)
/* 112:    */   {
/* 113:216 */     this.activo = activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isPredeterminado()
/* 117:    */   {
/* 118:225 */     return this.predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPredeterminado(boolean predeterminado)
/* 122:    */   {
/* 123:235 */     this.predeterminado = predeterminado;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.NivelInstruccion
 * JD-Core Version:    0.7.0.1
 */