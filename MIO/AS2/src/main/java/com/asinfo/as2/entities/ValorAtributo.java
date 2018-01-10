/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="valor_atributo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_atributo", "codigo"})})
/*  17:    */ public class ValorAtributo
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="valor_atributo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="valor_atributo")
/*  24:    */   @Column(name="id_valor_atributo")
/*  25:    */   private int idValorAtributo;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=10)
/*  31:    */   @Size(min=1, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_atributo")
/*  45:    */   private Atributo atributo;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49:101 */     return this.idValorAtributo;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdValorAtributo()
/*  53:    */   {
/*  54:110 */     return this.idValorAtributo;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdValorAtributo(int idValorAtributo)
/*  58:    */   {
/*  59:120 */     this.idValorAtributo = idValorAtributo;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64:129 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69:139 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74:148 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:158 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getCodigo()
/*  83:    */   {
/*  84:167 */     return this.codigo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setCodigo(String codigo)
/*  88:    */   {
/*  89:177 */     this.codigo = codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNombre()
/*  93:    */   {
/*  94:186 */     return this.nombre;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNombre(String nombre)
/*  98:    */   {
/*  99:196 */     this.nombre = nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:205 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:215 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isActivo()
/* 113:    */   {
/* 114:224 */     return this.activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivo(boolean activo)
/* 118:    */   {
/* 119:234 */     this.activo = activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isPredeterminado()
/* 123:    */   {
/* 124:243 */     return this.predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPredeterminado(boolean predeterminado)
/* 128:    */   {
/* 129:253 */     this.predeterminado = predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Atributo getAtributo()
/* 133:    */   {
/* 134:262 */     return this.atributo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setAtributo(Atributo atributo)
/* 138:    */   {
/* 139:272 */     this.atributo = atributo;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ValorAtributo
 * JD-Core Version:    0.7.0.1
 */