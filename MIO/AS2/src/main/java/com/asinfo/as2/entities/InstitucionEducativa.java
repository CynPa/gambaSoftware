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
/*  13:    */ @Table(name="institucion_educativa", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class InstitucionEducativa
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 4298796823180980387L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="institucion_educativa", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="institucion_educativa")
/*  21:    */   @Column(name="id_institucion_educativa")
/*  22:    */   private int idInstitucionEducativa;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="codigo", nullable=false)
/*  28:    */   @Size(min=1, max=10)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false)
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
/*  43: 81 */     return this.idInstitucionEducativa;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdInstitucionEducativa()
/*  47:    */   {
/*  48: 94 */     return this.idInstitucionEducativa;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdInstitucionEducativa(int idInstitucionEducativa)
/*  52:    */   {
/*  53:102 */     this.idInstitucionEducativa = idInstitucionEducativa;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58:110 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63:118 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68:126 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73:134 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCodigo()
/*  77:    */   {
/*  78:142 */     return this.codigo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCodigo(String codigo)
/*  82:    */   {
/*  83:150 */     this.codigo = codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombre()
/*  87:    */   {
/*  88:158 */     return this.nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNombre(String nombre)
/*  92:    */   {
/*  93:166 */     this.nombre = nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getDescripcion()
/*  97:    */   {
/*  98:174 */     return this.descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDescripcion(String descripcion)
/* 102:    */   {
/* 103:182 */     this.descripcion = descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isActivo()
/* 107:    */   {
/* 108:190 */     return this.activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setActivo(boolean activo)
/* 112:    */   {
/* 113:198 */     this.activo = activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isPredeterminado()
/* 117:    */   {
/* 118:206 */     return this.predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPredeterminado(boolean predeterminado)
/* 122:    */   {
/* 123:214 */     this.predeterminado = predeterminado;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.InstitucionEducativa
 * JD-Core Version:    0.7.0.1
 */